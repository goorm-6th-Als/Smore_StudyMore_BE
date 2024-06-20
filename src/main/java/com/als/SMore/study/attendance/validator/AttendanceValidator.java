package com.als.SMore.study.attendance.validator;

import com.als.SMore.domain.entity.*;
import com.als.SMore.domain.repository.MemberRepository;
import com.als.SMore.domain.repository.StudyLearningTimeRepository;
import com.als.SMore.domain.repository.StudyMemberRepository;
import com.als.SMore.domain.repository.StudyRepository;
import com.als.SMore.global.CustomErrorCode;
import com.als.SMore.global.CustomException;
import com.als.SMore.study.attendance.DTO.request.LearningMonthRequestDTO;
import com.als.SMore.study.attendance.DTO.response.LearningMonthListResponseDTO;
import com.als.SMore.study.attendance.DTO.response.LearningMonthResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Slf4j
@Component
@RequiredArgsConstructor
public class AttendanceValidator {
    private final MemberRepository memberRepository;
    private final StudyRepository studyRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final StudyLearningTimeRepository studyLearningTimeRepository;
    public Member getMember(Long pk) {
        // 멤버 PK를 받아서 검증
        Optional<Member> member = memberRepository.findById(pk);
        return member.orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_USER));
    }

    public Study getStudy(Long pk) {
        // Study PK를 받아서 검증
        Optional<Study> study = studyRepository.findById(pk);
        return study.orElseThrow(()-> new CustomException(CustomErrorCode.NOT_FOUND_STUDY));

    }

    public StudyMember getStudyMember(Member member, Study study){
        Optional<StudyMember> optionalStudyMember = studyMemberRepository.findByMemberAndStudy(member, study);
        return optionalStudyMember.orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_USER_OR_STUDY));

    }

    public Long studyTimeCalculator(AttendanceCheck attendanceCheck){
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

    public LearningMonthListResponseDTO getLearningMonth(StudyMember studyMember, LearningMonthRequestDTO learningMonthRequestDTO) {
        int year = learningMonthRequestDTO.getYear();

        int month = learningMonthRequestDTO.getMonth();
        log.info("{}",YearMonth.of(year, month).atDay(1));
        LocalDate start = YearMonth.of(year, month).atDay(1);

        LocalDate end = YearMonth.of(year, month).atEndOfMonth();

        List<StudyLearningTime> studyLearningTimeList = studyLearningTimeRepository.findByStudyMemberAndLearningDateBetween(studyMember, start, end);

        List<LearningMonthResponseDTO> learningMonthResponseDTOList = new ArrayList<>();
        Long totalTime = 0L;
        for (StudyLearningTime studyLearningTime : studyLearningTimeList) {
            LocalDate date = studyLearningTime.getLearningDate();
            Long LearningTime = studyLearningTime.getLearningTime();
            totalTime += LearningTime;
            LocalTime time = LocalTime.MIDNIGHT.plusSeconds(LearningTime);
            learningMonthResponseDTOList.add(new LearningMonthResponseDTO(date, time));
        }
        
        return new LearningMonthListResponseDTO(learningMonthResponseDTOList, totalTime);
    }
}
