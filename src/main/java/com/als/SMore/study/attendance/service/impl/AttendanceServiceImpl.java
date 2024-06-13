package com.als.SMore.study.attendance.service.impl;

import com.als.SMore.domain.entity.*;
import com.als.SMore.domain.repository.*;
import com.als.SMore.study.attendance.exception.AttendanceErrorCode;
import com.als.SMore.study.attendance.exception.AttendanceException;
import com.als.SMore.study.attendance.service.AttendanceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {
    final private MemberRepository memberRepository;
    final private StudyRepository studyRepository;
    final private AttendanceCheckRepository attendanceCheckRepository;
    final private StudyMemberRepository studyMemberRepository;
    final private StudyLearningTimeRepository studyLearningTimeRepository;

    private Member getMember(Long pk) {
        log.info("[getMember] Start");
        Optional<Member> member = memberRepository.findById(pk);
        if (member.isPresent()) return member.get();
        else throw new AttendanceException(AttendanceErrorCode.NOT_FOUND_USER);
    }


    private Study getStudy(Long pk) {
        log.info("[getStudy] Start");

        Optional<Study> study = studyRepository.findById(pk);
        if (study.isPresent()) return study.get();
        else throw new AttendanceException(AttendanceErrorCode.NOT_FOUND_STUDY);
    }

    /**
     * @param member
     * @param study
     * @return 출석시작 메서드
     */
    private AttendanceCheck attendanceStart(Member member, Study study) {
        log.info("[attendanceStart] Start");
        //meber와 study를 통해 attendanceCheck를 조회
        Optional<AttendanceCheck> attendance = attendanceCheckRepository
                .findTopByMemberAndStudyOrderByAttendanceCheckPkDesc(member, study);
        //찾아온 값이 없는 경우
        if (!attendance.isPresent()) {
            return createAttendance(member, study);
        }
        AttendanceCheck result = attendance.get();
        //
        return attendanceStartCheck(result) ? result : createAttendance(member, study);
    }

    /**
     * @param attendanceCheck
     * @return 예외처리 메소드 : 문제가 없다면 true
     */
    private Boolean attendanceStartCheck(AttendanceCheck attendanceCheck) {
        log.info("[attendanceStartCheck] Start");
        //출석이 오늘 날짜가 아닌경우
        //퇴장 시간이 지금 시간보다 지났을 경우
        if (attendanceCheck.getAttendanceDate().isEqual(attendanceCheck.getAttendanceDateEnd())) {
            log.info("퇴장 기록이 없어요");
            return true;
        }

        return false;
    }

    /**
     * @param member
     * @param study
     * @return 출석을 만드는 메서드
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
     * @param member
     * @param study  출석종료
     */
    private Long attendanceCheckEnd(Member member, Study study) {
        log.info("[attendanceCheckEnd] Start");
        Optional<AttendanceCheck> attendance = attendanceCheckRepository
                .findTopByMemberAndStudyOrderByAttendanceCheckPkDesc(member, study);
        Long answer = 0L;
        if (!attendance.isPresent()) log.info("첫 출석");
        AttendanceCheck attendanceEnd = attendance.get();

        if (attendanceEndCheck(attendanceEnd)) {
            attendanceEnd = attendanceEnd.toBuilder()
                    .attendanceDateEnd(LocalDateTime.now())
                    .build();
            log.info("퇴장시간 = {}", attendanceEnd.getAttendanceDateEnd());
            answer = setLearningTime(member,study,attendanceCheckRepository.save(attendanceEnd));
            System.out.println(answer);
            log.info("출석종료 성공");

        }
        return answer;
    }

    private Boolean attendanceEndCheck(AttendanceCheck attendanceCheck) {
        log.info("[attendanceEndCheck] Start");
        if (!attendanceCheck.getAttendanceDateEnd().isEqual(attendanceCheck.getAttendanceDate())) {
            log.info("출석 기록이 없습니다. 저장된 시간 : {}\n 지금시간 : {}", attendanceCheck.getAttendanceDateEnd(), LocalDateTime.now());
            log.info("퇴장실패");
            return false;
        }

        return true;
    }

    private StudyLearningTime createStudyLearningTime(StudyMember studyMember){
        return studyLearningTimeRepository.save(StudyLearningTime.builder()
                        .learningDate(LocalDate.now())
                        .learningTime(0L)
                        .studyMember(studyMember)
                        .build());
    }

   
    /**
     *
     * @param member
     * @param study
     * @param attendanceCheck
     * @return
     *
     */
    private Long setLearningTime(Member member, Study study, AttendanceCheck attendanceCheck) {
        Optional<StudyMember> optionalStudyMember = studyMemberRepository.findByMemberAndStudy(member, study);
        StudyMember studyMember;
        if(optionalStudyMember.isPresent()) studyMember = optionalStudyMember.get();
        else throw new RuntimeException();// 이 자리에 예외처리좀 해줘~ 스터디나 멤버가 없어서 조회가 안되면 이거 터진다~
        int startTime = (attendanceCheck.getAttendanceDate().getHour() * 60 * 60)
                + (attendanceCheck.getAttendanceDate().getMinute() * 60)
                + (attendanceCheck.getAttendanceDate().getSecond());
        int endTime = (attendanceCheck.getAttendanceDateEnd().getHour() * 60 * 60)
                + (attendanceCheck.getAttendanceDateEnd().getMinute() * 60)
                + (attendanceCheck.getAttendanceDateEnd().getSecond());
        Long learningTime = (long) (endTime - startTime);

        Optional<StudyLearningTime> optionalStudyLearningTime =
                studyLearningTimeRepository.findByStudyMemberAndLearningDate(studyMember, LocalDate.now());
        StudyLearningTime studyLearningTime;
        studyLearningTime = optionalStudyLearningTime.orElseGet(() -> createStudyLearningTime(studyMember));

        studyLearningTime = studyLearningTime.toBuilder()
                .learningTime(studyLearningTime.getLearningTime() + learningTime)
                .build();
        return studyLearningTimeRepository.save(studyLearningTime).getLearningTime();
    }

    @Override
    public LocalDateTime attendanceStart(Long memberPk, Long StudyPk) {
        log.info("[attendanceStart] Start");
        Member member = getMember(memberPk);
        Study study = getStudy(StudyPk);
        return attendanceStart(member, study).getAttendanceDate();
    }

    @Override
    public Long attendanceEnd(Long memberPk, Long StudyPk) {
        log.info("[attendanceEnd] Start");
        Member member = getMember(memberPk);
        Study study = getStudy(StudyPk);
        return attendanceCheckEnd(member, study);
    }
}
