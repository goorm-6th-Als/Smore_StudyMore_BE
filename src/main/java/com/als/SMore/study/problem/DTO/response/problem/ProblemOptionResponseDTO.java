package com.als.SMore.study.problem.DTO.response.problem;

import com.als.SMore.domain.entity.Problem;
import com.als.SMore.domain.entity.ProblemOptions;
import com.als.SMore.global.json.LongToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProblemOptionResponseDTO {
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long problemOptionPk;
    private String content;
    private Integer num;


    public static ProblemOptionResponseDTO of(ProblemOptions problemOptions) {
        return ProblemOptionResponseDTO.builder()
                .problemOptionPk(problemOptions.getProblemOptionsPk())
                .content(problemOptions.getOptionsContent())
                .num(problemOptions.getOptionsNum())
                .build();
    }

}
