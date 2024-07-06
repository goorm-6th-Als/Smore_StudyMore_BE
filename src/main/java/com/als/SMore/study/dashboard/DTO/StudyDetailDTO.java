package com.als.SMore.study.dashboard.DTO;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudyDetailDTO {
    private String studyName;
    private int maxPeople;
    private String content;
    private LocalDate startDate;
    private LocalDate closeDate;
}