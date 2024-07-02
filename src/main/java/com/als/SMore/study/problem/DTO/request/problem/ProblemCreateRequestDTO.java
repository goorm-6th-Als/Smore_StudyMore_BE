package com.als.SMore.study.problem.DTO.request.problem;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(toBuilder = true)
public class ProblemCreateRequestDTO {
    private Long studyProblemBankPk;
    private String content;
    private Integer answer;
    private String explanation;

    private List<ProblemOptionRequestDTO> problemOptionRequestDTOList;

}
