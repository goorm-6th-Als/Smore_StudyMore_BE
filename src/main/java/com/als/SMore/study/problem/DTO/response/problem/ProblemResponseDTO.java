package com.als.SMore.study.problem.DTO.response.problem;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Builder(toBuilder = true)
@Getter // 문제를 반환 할 목적의 DTO
public class ProblemResponseDTO {
    private Long problemPk;
    private String memberNickname;
    private String studyBankName;
    private String problemTitle;
    private String problemContent;
    private String problemExplanation;
    private LocalDate problemDate;
    private List<ProblemOptionResponseDTO> options;
}
