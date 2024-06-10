package com.als.SMore.study.studyCRUD.DTO;

import com.als.SMore.domain.entity.StudyDetail;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StudyDetailDTO {
    private Long studyPk;
    private String imageUri;
    private int maxPeople;
    private String content;
    private LocalDate startDate;
    private LocalDate closeDate;

    public static StudyDetailDTO fromEntity(StudyDetail studyDetail) {
        return StudyDetailDTO.builder()
                .studyPk(studyDetail.getStudyPk())
                .imageUri(studyDetail.getImageUri())
                .maxPeople(studyDetail.getMaxPeople())
                .content(studyDetail.getContent())
                .startDate(studyDetail.getStartDate())
                .closeDate(studyDetail.getCloseDate())
                .build();
    }
}
