

package com.als.SMore.study.problem.service;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.study.problem.DTO.DTOAndPermission;
import com.als.SMore.study.problem.DTO.request.ProblemBankUpdateRequestDTO;
import com.als.SMore.study.problem.DTO.response.ProblemBankResponseDTO;
import com.als.SMore.study.problem.DTO.response.ProblemTitleResponseDTO;

import java.util.List;

public interface ProblemService {
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

    //<readOnly>
    //문제 리스트
    void createProblem();

    List<ProblemTitleResponseDTO> getAllProblemTitle(Long ProblemBankPk);


}
