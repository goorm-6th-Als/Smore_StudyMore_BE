package com.als.SMore.study.problem.DTO.response.problem;

import com.als.SMore.global.json.LongToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProblemOptionResponseDTO {
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long problemOptionPk;
    private String content;
    private Integer num;

}
