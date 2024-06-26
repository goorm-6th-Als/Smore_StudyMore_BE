package com.als.SMore.user.mystudy.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class StudyResponse {
    private Long studyPk;
    private String studyName;
    private String studyImg;
    private Long studyPersonNum;
    private LocalDate studyStartDate;
    private LocalDate studyEndDate;

}
