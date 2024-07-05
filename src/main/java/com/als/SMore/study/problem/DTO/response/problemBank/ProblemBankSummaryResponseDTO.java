package com.als.SMore.study.problem.DTO.response.problemBank;

import com.als.SMore.domain.entity.StudyProblemBank;
import com.als.SMore.global.json.LongToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProblemBankSummaryResponseDTO {
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long problemBankPk;
    private String problemBankName;
    private int count;

    public static ProblemBankSummaryResponseDTO of(StudyProblemBank studyProblemBank, int count){
        return ProblemBankSummaryResponseDTO.builder().
                problemBankPk(studyProblemBank.getStudyProblemBankPk()).
                problemBankName(studyProblemBank.getBankName()).
                count(count).
                build();
    }

}
