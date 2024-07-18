

package com.als.SMore.study.problem.service;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.StudyProblemBank;
import com.als.SMore.study.problem.DTO.request.problemBank.ProblemBankUpdateRequestDTO;
import com.als.SMore.study.problem.DTO.response.problemBank.PersonalProblemBankResponseDTO;
import com.als.SMore.study.problem.DTO.response.problemBank.ProblemBankResponseDTO;
import com.als.SMore.study.problem.DTO.response.problemBank.ProblemBankSummaryResponseDTO;

import java.util.List;

public interface ProblemBankService {
    //스터디당 30개 이하
    Long createProblemBank(Member member, Study study, String bankName);

    //<readOnly>
    //문제 그룹 리스트
    List<ProblemBankResponseDTO> getAllProblemBank(Member member, Study study);

    //<readOnly>
    //문제 그룹
    ProblemBankResponseDTO getProblemBank(StudyProblemBank problemBank, Member member);

    //문제그룹당 100개 이하


    void deleteProblemBank(Member member, StudyProblemBank problemBank);

    ProblemBankResponseDTO updateProblemBank(Member member, ProblemBankUpdateRequestDTO problemBankUpdateRequestDTO);

    List<PersonalProblemBankResponseDTO> getPersonalProblemBank(Member member, Study study);

    List<ProblemBankSummaryResponseDTO> getAllProblemBankSummary(Study study);
}
