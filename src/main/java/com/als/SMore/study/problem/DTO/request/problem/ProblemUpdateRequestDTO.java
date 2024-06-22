package com.als.SMore.study.problem.DTO.request.problem;

import com.als.SMore.study.problem.DTO.response.problem.ProblemOptionResponseDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ProblemUpdateRequestDTO {
    private Long problemPk;
    private String problemTitle;
    private String problemContent;
    private Long problemAnswerPk;
    private String ProblemExplanation;

    private List<ProblemOptionResponseDTO> problemOptionResponseDTOList;
}
