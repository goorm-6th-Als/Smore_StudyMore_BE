package com.als.SMore.user.dto.mystudy.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class StudyResponse {
    private Long studyPk;
    private String studyName;
    private String studyImg;
    private Long studyPersomNum;
    private LocalDate studyStartDate;
    private LocalDate studyEndDate;

}
