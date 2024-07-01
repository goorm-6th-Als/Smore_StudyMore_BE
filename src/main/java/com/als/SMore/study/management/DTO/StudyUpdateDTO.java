package com.als.SMore.study.management.DTO;

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
}
