package com.als.SMore.study.studyCRUD.DTO;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StudyCreateDTO {
    private Long studyPk;
    private String studyName;
    private Long memberPk;
    private String imageUri;
    private int maxPeople;
    private String content;
    private LocalDate startDate;
    private LocalDate closeDate;
    private String studyUrl;
}
