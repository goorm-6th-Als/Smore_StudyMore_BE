package com.als.SMore.study.problem.DTO.response.problemBank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProblemBankResponseDTO {
    private Long pk;
    private String ProblemBankName;
    private String writer;
    private boolean authority;
}

