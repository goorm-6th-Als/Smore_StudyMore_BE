package com.als.SMore.study.problem.DTO.response.problemBank;

import com.als.SMore.global.json.LongToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProblemBankResponseDTO {
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long pk;
    private String ProblemBankName;
    private String writer;
    private boolean authority;
}

