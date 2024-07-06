package com.als.SMore.study.problem.DTO.response.problem;

import com.als.SMore.domain.entity.Problem;
import com.als.SMore.global.json.LongToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Builder(toBuilder = true)
@Getter // 문제를 반환 할 목적의 DTO
public class ProblemResponseDTO {
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long problemPk;
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long answerPk;
    private String memberNickname;
    private String studyBankName;
    private String problemContent;
    private String problemExplanation;

    private LocalDate problemDate;
    private List<ProblemOptionResponseDTO> options;

    public static ProblemResponseDTO of(Problem problem, List<ProblemOptionResponseDTO> problemOptionResponseDTOList) {
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
}
