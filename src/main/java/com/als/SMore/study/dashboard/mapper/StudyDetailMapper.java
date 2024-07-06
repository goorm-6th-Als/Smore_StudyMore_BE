package com.als.SMore.study.dashboard.mapper;

import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.StudyDetail;
import com.als.SMore.study.dashboard.DTO.StudyDetailDTO;

public class StudyDetailMapper {
    public static StudyDetailDTO toDTO(Study study,StudyDetail studyDetail) {
        return StudyDetailDTO.builder()
                .studyName(study.getStudyName())
                .maxPeople(studyDetail.getMaxPeople())
                .content(studyDetail.getContent())
                .startDate(studyDetail.getStartDate())
                .closeDate(studyDetail.getCloseDate())
                .build();
    }
}
