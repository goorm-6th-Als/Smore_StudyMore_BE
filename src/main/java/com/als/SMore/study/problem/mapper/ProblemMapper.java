package com.als.SMore.study.problem.mapper;

import com.als.SMore.domain.entity.Problem;
import com.als.SMore.domain.entity.ProblemOptions;
import com.als.SMore.study.problem.DTO.response.problem.ProblemOptionResponseDTO;
import com.als.SMore.study.problem.DTO.response.problem.ProblemResponseDTO;
import com.als.SMore.study.problem.DTO.response.problem.ProblemUpdateResponseDTO;

import java.util.List;

public class ProblemMapper {

    public static ProblemOptionResponseDTO problemOptionsToProblemOptionResponseDTO(ProblemOptions problemOptions) {
        return ProblemOptionResponseDTO.builder()
                .problemOptionPk(problemOptions.getProblemOptionsPk())
                .content(problemOptions.getOptionsContent())
                .num(problemOptions.getOptionsNum())
                .build();
    }

    public static ProblemResponseDTO problemAndProblemOptionResponseDTOToProblemResponseDTO(Problem problem, List<ProblemOptionResponseDTO> problemOptionResponseDTOList) {
        return ProblemResponseDTO.builder()
                .problemPk(problem.getProblemPk())
                .memberNickname(problem.getMember().getNickName())
                .studyBankName(problem.getStudyProblemBank().getBankName())
                .problemContent(problem.getProblemContent())
                .answerPk(problem.getProblemAnswerPk())
                .problemExplanation(problem.getProblemExplanation())
                .problemDate(problem.getCreateDate())
                .options(problemOptionResponseDTOList)
                .build();
    }

    public static ProblemUpdateResponseDTO problemAndProblemOptionResponseDTOToProblemUpdateResponseDTO(Problem problem, List<ProblemOptionResponseDTO> problemOptionResponseDTOList) {
        return ProblemUpdateResponseDTO.builder()
                .problemPk(problem.getProblemPk())
                .memberNickname(problem.getMember().getNickName())
                .studyBankName(problem.getStudyProblemBank().getBankName())
                .problemContent(problem.getProblemContent())
                .problemExplanation(problem.getProblemExplanation())
                .problemDate(problem.getCreateDate())
                .answerPk(problem.getProblemAnswerPk())
                .options(problemOptionResponseDTOList)
                .build();
    }
}
