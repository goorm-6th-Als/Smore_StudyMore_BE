package com.als.SMore.study.management.mapper;

import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.StudyBoard;
import com.als.SMore.domain.entity.StudyDetail;
import com.als.SMore.study.management.DTO.StudyDataDTO;

public class StudyDataMapper {

    public static StudyDataDTO toDTO(Study study,  StudyDetail studyDetail, StudyBoard studyBoard) {
        return StudyDataDTO.builder()
//                .studyPk(studyDetail.getStudyPk())
                .imageUri(studyBoard.getImageUri())
                .studyName(study.getStudyName())
                .maxPeople(studyDetail.getMaxPeople())
                .content(studyDetail.getContent())
                .startDate(studyDetail.getStartDate())
                .closeDate(studyDetail.getCloseDate())

                .adTitle(studyBoard.getAdTitle())
                .adContent(studyBoard.getAdContent())
                .build();
    }
}


