package com.als.SMore.study.attendance.service.impl;

import com.als.SMore.domain.entity.*;
import com.als.SMore.domain.repository.*;
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
    private AttendanceCheck attendanceStart(Member member, Study study) {
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
        LocalDateTime localDateTime = LocalDateTime.now();
        AttendanceCheck attendanceCheck = AttendanceCheck
                .builder()
                .attendanceDate(localDateTime)
                .attendanceDateEnd(localDateTime)
                .study(study)
                .member(member)
                .build();
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
            attendanceEnd = attendanceEnd.toBuilder()
                    .attendanceDateEnd(LocalDateTime.now())
                    .build();
            log.info("퇴장 시간 = {}", attendanceEnd.getAttendanceDateEnd());
            learningTime = setLearningTime(member, study, attendanceCheckRepository.save(attendanceEnd));
            System.out.println(learningTime);
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
        return studyLearningTimeRepository.save(StudyLearningTime.builder()
                .learningDate(LocalDate.now())
                .learningTime(0L)
                .studyMember(studyMember)
                .build());
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
        Long learningTime = attendanceValidator.studyTimeCalculator(attendanceCheck);

        StudyLearningTime studyLearningTime = getLearningTime(studyMember);
        studyLearningTime = studyLearningTime.toBuilder()
                .learningTime(studyLearningTime.getLearningTime() + learningTime)
                .build();
        return studyLearningTimeRepository.save(studyLearningTime).getLearningTime();
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
    public LocalDateTime attendanceStart(Long memberPk, Long StudyPk) {
        log.info("[attendanceStart] Start");
        Member member = attendanceValidator.getMember(memberPk);
        Study study = attendanceValidator.getStudy(StudyPk);
        return attendanceStart(member, study).getAttendanceDate();
    }

    @Transactional
    @Override
    public Long attendanceEnd(Long memberPk, Long StudyPk) {
        log.info("[attendanceEnd] Start");
        Member member = attendanceValidator.getMember(memberPk);
        Study study = attendanceValidator.getStudy(StudyPk);
        return attendanceCheckEnd(member, study);
    }

    @Override
    @Transactional
    public Long getLearningSeconds(Long memberPk, Long StudyPk) {
        Member member = attendanceValidator.getMember(memberPk);
        Study study = attendanceValidator.getStudy(StudyPk);
        return getLearningTime(attendanceValidator.getStudyMember(member, study)).getLearningTime();
    }

    @Override
    public LearningMonthListResponseDTO getLearningMonth(Long memberPk, Long studyPk, LearningMonthRequestDTO learningMonthRequestDTO) {
        Member member = attendanceValidator.getMember(memberPk);
        Study study = attendanceValidator.getStudy(studyPk);
        StudyMember studyMember = attendanceValidator.getStudyMember(member, study);
        return attendanceValidator.getLearningMonth(studyMember, learningMonthRequestDTO);
    }
}