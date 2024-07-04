package com.als.SMore.study.problem.mapper;

import com.als.SMore.domain.entity.StudyProblemBank;
import com.als.SMore.study.problem.DTO.response.problemBank.PersonalProblemBankResponseDTO;

public class ProblemBankMapper {


    // studyProblemBank -> PersonalProblemBankResponseDTO
    public static PersonalProblemBankResponseDTO
    studyProblemBankToPersonalProblemBankResponseDTO(StudyProblemBank studyProblemBank, int problemCnt) {
        return PersonalProblemBankResponseDTO.builder().
                problemBankPk(studyProblemBank.getStudyProblemBankPk()).
                problemBankName(studyProblemBank.getBankName()).
                count(problemCnt).
                build();
    }

}
