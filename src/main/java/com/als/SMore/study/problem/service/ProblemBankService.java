

package com.als.SMore.study.problem.service;

import com.als.SMore.study.problem.DTO.request.problemBank.ProblemBankUpdateRequestDTO;
import com.als.SMore.study.problem.DTO.response.problemBank.PersonalProblemBankResponseDTO;
import com.als.SMore.study.problem.DTO.response.problemBank.ProblemBankResponseDTO;

import java.util.List;

public interface ProblemBankService {
    //스터디당 30개 이하
    void createProblemBank(Long memberPk, Long studyPk, String bankName);

    //<readOnly>
    //문제 그룹 리스트
    List<ProblemBankResponseDTO> getAllProblemBank(Long memberPk, Long studyPk);

    //<readOnly>
    //문제 그룹
    ProblemBankResponseDTO getProblemBank(Long problemBankPk, Long memberPk);

    //문제그룹당 100개 이하


    void deleteProblemBank(Long memberPk, Long problemBankPk);

    ProblemBankResponseDTO updateProblemBank(Long memberPk, ProblemBankUpdateRequestDTO problemBankUpdateRequestDTO);

    List<PersonalProblemBankResponseDTO> getPersonalProblemBank(Long memberPk, Long studyPk);


}
