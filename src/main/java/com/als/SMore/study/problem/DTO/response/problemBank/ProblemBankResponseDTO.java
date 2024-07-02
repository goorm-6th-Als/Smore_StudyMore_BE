package com.als.SMore.study.problem.DTO.response.problemBank;

import com.als.SMore.global.json.LongToStringSerializer;
import com.als.SMore.study.problem.DTO.response.problem.ProblemResponseDTO;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.util.List;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProblemBankResponseDTO {
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long pk;
    private String ProblemBankName;
    private String writer;
    private boolean authority;
    private List<ProblemResponseDTO> problemList;
}

