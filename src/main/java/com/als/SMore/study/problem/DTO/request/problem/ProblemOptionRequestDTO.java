package com.als.SMore.study.problem.DTO.request.problem;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProblemOptionRequestDTO {
    private String content;
    private Integer num;
}
