package com.als.SMore.study.management.mapper;

import com.als.SMore.domain.entity.StudyBoard;
import com.als.SMore.domain.entity.StudyDetail;
import com.als.SMore.study.management.DTO.StudyUpdateDTO;
import java.time.LocalDate;

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

    public static StudyBoard updateStudyBoard(StudyBoard studyBoard, StudyUpdateDTO studyUpdateDTO) {
        String adSummary = studyUpdateDTO.getContent() != null && studyUpdateDTO.getContent().length() > 30
                ? studyUpdateDTO.getContent().substring(0, 30) + "..."
                : studyUpdateDTO.getContent() != null ? studyUpdateDTO.getContent() : studyBoard.getAdContent();

        return studyBoard.toBuilder()
                .adContent(studyUpdateDTO.getContent() != null ? studyUpdateDTO.getContent() : studyBoard.getAdContent())
                .imageUri(studyUpdateDTO.getImageUri() != null ? studyUpdateDTO.getImageUri() : studyBoard.getImageUri())
                .adSummary(adSummary)
                .modifyDate(LocalDate.now())
                .build();
    }
}
