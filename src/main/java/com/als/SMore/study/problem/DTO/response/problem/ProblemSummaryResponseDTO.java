package com.als.SMore.study.problem.DTO.response.problem;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class ProblemSummaryResponseDTO {
    private Long Pk; // problem_Pk
    private String writer; //nickname
    private String Title; //problemTitle
}
