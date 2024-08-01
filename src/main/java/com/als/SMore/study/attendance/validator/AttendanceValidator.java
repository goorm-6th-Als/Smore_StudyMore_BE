package com.als.SMore.study.attendance.validator;

import com.als.SMore.domain.entity.*;
import com.als.SMore.domain.repository.MemberRepository;
import com.als.SMore.domain.repository.StudyLearningTimeRepository;
import com.als.SMore.domain.repository.StudyMemberRepository;
import com.als.SMore.domain.repository.StudyRepository;
import com.als.SMore.global.exception.CustomErrorCode;
import com.als.SMore.global.exception.CustomException;
import com.als.SMore.study.attendance.DTO.request.LearningMonthRequestDTO;
import com.als.SMore.study.attendance.DTO.response.LearningMonthListResponseDTO;
import com.als.SMore.study.attendance.DTO.response.LearningMonthResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Slf4j
@Component
@RequiredArgsConstructor
public class AttendanceValidator {
    private final StudyMemberRepository studyMemberRepository;
    private final StudyLearningTimeRepository studyLearningTimeRepository;

    public StudyMember getStudyMember(Member member, Study study){
        Optional<StudyMember> optionalStudyMember = studyMemberRepository.findByMemberAndStudy(member, study);
        return optionalStudyMember.orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_USER_OR_STUDY));

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
