package com.als.SMore.study.problem.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

