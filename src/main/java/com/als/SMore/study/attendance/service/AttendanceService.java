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

    LocalDateTime attendanceStart(Long memberPk, Long StudyPk);
    Long attendanceEnd(Long memberPk, Long StudyPk);
    Long getLearningSeconds(Long memberPk, Long StudyPk);
    LearningMonthListResponseDTO getLearningMonth(Long memberPk, Long studyPk, LearningMonthRequestDTO learningMonthRequestDTO);
}
