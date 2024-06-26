package com.als.SMore.study.problem.DTO.request.problemBank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProblemBankUpdateRequestDTO {
    private Long problemBankPk;
    private String problemBankName;
}
