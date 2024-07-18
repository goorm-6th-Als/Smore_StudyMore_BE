package com.als.SMore.study.attendance.service;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.StudyLearningTime;
import com.als.SMore.domain.entity.StudyMember;
import com.als.SMore.study.attendance.DTO.request.LearningMonthRequestDTO;
import com.als.SMore.study.attendance.DTO.response.LearningMonthListResponseDTO;
import com.als.SMore.study.attendance.DTO.response.LearningMonthResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface AttendanceService {

    LocalDateTime attendanceStart(Member member, Study study);
    Long attendanceEnd(Member member, Study study);
    Long getLearningSeconds(StudyMember studyMember);
    LearningMonthListResponseDTO getLearningMonth(StudyMember studyMember, LearningMonthRequestDTO learningMonthRequestDTO);
}
