package com.als.SMore.study.problem.DTO.request.problem;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ProblemGetAllRequestDTO {
    private List<Long> studyProblemBankPk;
    private Integer max;
}
