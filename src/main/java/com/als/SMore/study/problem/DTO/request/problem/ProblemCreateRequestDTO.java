package com.als.SMore.study.problem.DTO.request.problem;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class ProblemCreateRequestDTO {
    private Long studyProblemBankPk;
    private String title;
    private String content;
    private int answer;
    private String explanation;
}
