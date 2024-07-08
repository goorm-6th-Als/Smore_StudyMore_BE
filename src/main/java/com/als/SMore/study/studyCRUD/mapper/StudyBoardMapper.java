package com.als.SMore.study.studyCRUD.mapper;

import com.als.SMore.domain.entity.StudyBoard;
import com.als.SMore.domain.entity.StudyDetail;
import com.als.SMore.study.studyCRUD.DTO.StudyBoardDTO;

public class StudyBoardMapper {
    public static StudyBoardDTO toDTO(StudyBoard studyBoard, StudyDetail studyDetail, boolean includeContent) {
        return StudyBoardDTO.builder()
                .studyBoardPk(studyBoard.getStudyBoardPk())
                .studyPk(studyBoard.getStudy().getStudyPk())
                .adTitle(studyBoard.getAdTitle())

                .adContent(includeContent ? studyBoard.getAdContent() : null)
                .adSummary(includeContent ? null : studyBoard.getAdSummary())
                .maxPeople(studyDetail.getMaxPeople())
                .startDate(studyDetail.getStartDate())
                .closeDate(studyDetail.getCloseDate())
                .modifyDate(studyBoard.getModifyDate())
                .build();
    }
}