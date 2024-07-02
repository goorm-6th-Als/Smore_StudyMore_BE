package com.als.SMore.study.management.mapper;

import com.als.SMore.domain.entity.StudyDetail;
import com.als.SMore.study.management.DTO.StudyUpdateDTO;

public class StudyUpdateMapper {

    public static StudyUpdateDTO fromEntity(StudyDetail studyDetail) {
        return StudyUpdateDTO.builder()
                .content(studyDetail.getContent())
                .imageUri(studyDetail.getImageUri())
                .maxPeople(studyDetail.getMaxPeople())
                .closeDate(studyDetail.getCloseDate())
                .build();
    }

    public static StudyDetail updateStudyDetail(StudyDetail studyDetail, StudyUpdateDTO studyUpdateDTO) {
        return studyDetail.toBuilder()
                .content(studyUpdateDTO.getContent() != null ? studyUpdateDTO.getContent() : studyDetail.getContent())
                .imageUri(studyUpdateDTO.getImageUri() != null ? studyUpdateDTO.getImageUri() : studyDetail.getImageUri())
                .maxPeople(studyUpdateDTO.getMaxPeople() != null ? studyUpdateDTO.getMaxPeople() : studyDetail.getMaxPeople())
                .closeDate(studyUpdateDTO.getCloseDate() != null ? studyUpdateDTO.getCloseDate() : studyDetail.getCloseDate())
                .build();
    }
}
