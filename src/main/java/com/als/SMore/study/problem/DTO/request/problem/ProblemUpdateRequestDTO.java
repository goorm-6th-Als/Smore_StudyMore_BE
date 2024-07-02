package com.als.SMore.study.problem.DTO.request.problem;

import com.als.SMore.study.problem.DTO.response.problem.ProblemOptionResponseDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ProblemUpdateRequestDTO {

    private Long problemPk;
    private String problemContent;
    private Integer answer;
    private String ProblemExplanation;

    private List<ProblemOptionRequestDTO> problemOptionRequestDTOList;
}
