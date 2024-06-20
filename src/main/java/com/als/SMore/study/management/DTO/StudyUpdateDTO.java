package com.als.SMore.study.management.DTO;

import com.als.SMore.domain.entity.StudyBoard;
import com.als.SMore.domain.entity.StudyDetail;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class StudyUpdateDTO {
    private String content;
    private String imageUri;
    private Integer maxPeople;
    private LocalDate closeDate;

    public static StudyUpdateDTO fromEntity(StudyDetail studyDetail) {
        return StudyUpdateDTO.builder()
                .content(studyDetail.getContent())
                .imageUri(studyDetail.getImageUri())
                .maxPeople(studyDetail.getMaxPeople())
                .closeDate(studyDetail.getCloseDate())
                .build();
    }

    public StudyDetail updateDetailEntity(StudyDetail studyDetail) {
        return studyDetail.toBuilder()
                .content(this.content != null ? this.content : studyDetail.getContent())
                .imageUri(this.imageUri != null ? this.imageUri : studyDetail.getImageUri())
                .maxPeople(this.maxPeople != null ? this.maxPeople : studyDetail.getMaxPeople())
                .closeDate(this.closeDate != null ? this.closeDate : studyDetail.getCloseDate())
                .build();
    }

    public StudyBoard updateBoardEntity(StudyBoard studyBoard) {
        String adSummary = this.content != null && this.content.length() > 30
                ? this.content.substring(0, 30) + "..."
                : this.content != null ? this.content : studyBoard.getAdContent();

        return studyBoard.toBuilder()
                .adContent(this.content != null ? this.content : studyBoard.getAdContent())
                .imageUri(this.imageUri != null ? this.imageUri : studyBoard.getImageUri())
                .adSummary(adSummary)
                .modifyDate(LocalDate.now())
                .build();
    }
}
