package com.als.SMore.study.management.DTO;

import com.als.SMore.domain.entity.StudyDetail;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

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
}
