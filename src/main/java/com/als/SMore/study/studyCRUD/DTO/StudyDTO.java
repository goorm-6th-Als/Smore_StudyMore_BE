package com.als.SMore.study.studyCRUD.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyDTO {

    private String studyName;
    private String imageUri;
    private String overView;
    private String openTime;
    private String endTime;
    private int maxPeople;
}