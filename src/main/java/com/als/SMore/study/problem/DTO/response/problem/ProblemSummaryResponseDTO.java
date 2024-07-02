package com.als.SMore.study.problem.DTO.response.problem;

import com.als.SMore.global.json.LongToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class ProblemSummaryResponseDTO {
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long Pk; // problem_Pk
    private String writer; //nickname
}
