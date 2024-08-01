package com.als.SMore.study.attendance.service.impl;

import com.als.SMore.domain.entity.*;
import com.als.SMore.domain.repository.*;
import com.als.SMore.global.exception.CustomErrorCode;
import com.als.SMore.global.exception.CustomException;
import com.als.SMore.study.attendance.DTO.request.LearningMonthRequestDTO;
import com.als.SMore.study.attendance.DTO.response.LearningMonthListResponseDTO;
import com.als.SMore.study.attendance.service.AttendanceService;
import com.als.SMore.study.attendance.validator.AttendanceValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {
    final private AttendanceCheckRepository attendanceCheckRepository;
    final private StudyLearningTimeRepository studyLearningTimeRepository;
    final private AttendanceValidator attendanceValidator;

    /**
     * Member와 Study를 통해 attendance를 조회하고 값이 없는 경우 새로 생성
     * 조회된 attendance 날짜가 오늘인지 확인 후 아니면 새로 생성
     * 오늘인 경우 퇴장 기록 확인 후 퇴장 기록이 없을 시 조회된 출석 시간 반환
     * 퇴장 기록 확인이 된다면 새로 생성
     * @param member
     * @param study
     * @return 출석 시작 메서드
     */
    private AttendanceCheck getAttendance(Member member, Study study) {
        log.info("[attendanceStart] Start");
        // member와 study를 통해 attendanceCheck를 조회
        Optional<AttendanceCheck> attendance = attendanceCheckRepository
                .findTopByMemberAndStudyOrderByAttendanceCheckPkDesc(member, study);
        // 찾아온 값이 없는 경우
        if (!attendance.isPresent()) {
            return createAttendance(member, study);
        }
        AttendanceCheck result = attendance.get();
        // 퇴장 기록, 날짜 확인
        return attendanceStartCheck(result) ? result : createAttendance(member, study);
    }

    /**
     * 체크 목록
     * 날짜가 오늘인지
     * 퇴장 기록이 있는지
     * @param attendanceCheck
     * @return 예외 처리 메소드 : 문제가 없다면 true
     */
    private Boolean attendanceStartCheck(AttendanceCheck attendanceCheck) {
        log.info("[attendanceStartCheck] Start");
        // 출석이 오늘 날짜가 아닌 경우
        // 퇴장 시간이 지금 시간보다 지났을 경우
        if (attendanceCheck.getAttendanceDate().isEqual(attendanceCheck.getAttendanceDateEnd())) {
            log.info("퇴장 기록이 없어요");
            return true;
        }
        return false;
    }

    /**
     * 출석을 만드는 메서드
     * @param member
     * @param study
     * @return
     */
    private AttendanceCheck createAttendance(Member member, Study study) {
        log.info("[createAttendance] Start");
        AttendanceCheck attendanceCheck = AttendanceCheck.of(member, study, LocalDateTime.now());
        return attendanceCheckRepository.save(attendanceCheck);
    }

    /**
     * 퇴장 시간을 업데이트후 공부시간 업데이트
     * @param member 조회하려는 멤버
     * @param study 조회하려는 스터디
     * @return
     */
    private Long attendanceCheckEnd(Member member, Study study) {
        log.info("[attendanceCheckEnd] Start");
        Optional<AttendanceCheck> attendance = attendanceCheckRepository
                .findTopByMemberAndStudyOrderByAttendanceCheckPkDesc(member, study);
        Long learningTime = 0L;
        AttendanceCheck attendanceEnd = attendance.get();

        if (attendanceEndCheck(attendanceEnd)) {
            attendanceEnd.updateAttendanceDateEnd();
            log.info("퇴장 시간 = {}", attendanceEnd.getAttendanceDateEnd());
            learningTime = setLearningTime(member, study, attendanceEnd);
            log.info("출석 종료 성공");
        }
        return learningTime;
    }

    /**
     * 출석 종료 체크 메서드
     * @param attendanceCheck
     * @return 문제가 없다면 true
     */
    private Boolean attendanceEndCheck(AttendanceCheck attendanceCheck) {
        log.info("[attendanceEndCheck] Start");
        if (!attendanceCheck.getAttendanceDateEnd().isEqual(attendanceCheck.getAttendanceDate())) {
            log.info("출석 기록이 없습니다. 저장된 시간 : {}\n 지금 시간 : {}", attendanceCheck.getAttendanceDateEnd(), LocalDateTime.now());
            log.info("퇴장 실패");
            return false;
        }
        return true;
    }

    /**
     * StudyLearningTime 객체 생성
     * @param studyMember
     * @return 생성된 StudyLearningTime 객체
     */
    private StudyLearningTime createStudyLearningTime(StudyMember studyMember) {
        return studyLearningTimeRepository.save(StudyLearningTime.of(studyMember));
    }

    private Long studyTimeCalculator(AttendanceCheck attendanceCheck){
        // AttendanceCheck를 받아 출석시작과 끝을 비교해 초 단위로 return
        int startTime = (attendanceCheck.getAttendanceDate().getHour() * 60 * 60)//시
                + (attendanceCheck.getAttendanceDate().getMinute() * 60)//분
                + (attendanceCheck.getAttendanceDate().getSecond());//초
        int endTime = (attendanceCheck.getAttendanceDateEnd().getHour() * 60 * 60)//시
                + (attendanceCheck.getAttendanceDateEnd().getMinute() * 60)//분
                + (attendanceCheck.getAttendanceDateEnd().getSecond());//초
        Long result = (long)(endTime - startTime);
        if(result < 0) {
            log.debug("공부 시간이 0초보다 작습니다");
            throw new CustomException(CustomErrorCode.INVALID_VALUE);
        }
        return result;
    }

    /**
     * 학습 시간을 설정하는 메서드
     * @param member
     * @param study
     * @param attendanceCheck
     * @return 학습 시간 (초)
     */
    private Long setLearningTime(Member member, Study study, AttendanceCheck attendanceCheck) {
        StudyMember studyMember = attendanceValidator.getStudyMember(member, study);
        Long learningTime = studyTimeCalculator(attendanceCheck);

        StudyLearningTime studyLearningTime = getLearningTime(studyMember);
        studyLearningTime.updateLearningTime(learningTime);
        return studyLearningTime.getLearningTime();
    }

    /**
     * 오늘 학습 시간을 가져오는 메서드
     * @param studyMember
     * @return StudyLearningTime 객체
     */
    public StudyLearningTime getLearningTime(StudyMember studyMember) {
        Optional<StudyLearningTime> optionalStudyLearningTime = studyLearningTimeRepository.findByStudyMemberAndLearningDate(studyMember, LocalDate.now());
        return optionalStudyLearningTime.orElseGet(() -> createStudyLearningTime(studyMember));
    }

    @Transactional
    @Override
    public LocalDateTime attendanceStart(Member member, Study study) {
        log.info("[attendanceStart] Start");
        return getAttendance(member, study).getAttendanceDate();
    }

    @Transactional
    @Override
    public Long attendanceEnd(Member member, Study study) {
        log.info("[attendanceEnd] Start");
        return attendanceCheckEnd(member, study);
    }

    @Override
    @Transactional
    public Long getLearningSeconds(StudyMember studyMember) {
        return getLearningTime(studyMember).getLearningTime();
    }

    @Override
    public LearningMonthListResponseDTO getLearningMonth(StudyMember studyMember, LearningMonthRequestDTO learningMonthRequestDTO) {
        return attendanceValidator.getLearningMonth(studyMember, learningMonthRequestDTO);
    }
}