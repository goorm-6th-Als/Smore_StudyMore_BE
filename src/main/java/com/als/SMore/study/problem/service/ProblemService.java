package com.als.SMore.study.problem.service;


import com.als.SMore.study.problem.DTO.request.problem.ProblemCreateRequestDTO;
import com.als.SMore.study.problem.DTO.request.problem.ProblemGetAllRequestDTO;
import com.als.SMore.study.problem.DTO.request.problem.ProblemUpdateRequestDTO;
import com.als.SMore.study.problem.DTO.response.problem.ProblemResponseDTO;
import com.als.SMore.study.problem.DTO.response.problem.ProblemSummaryResponseDTO;
import com.als.SMore.study.problem.DTO.response.problem.ProblemUpdateResponseDTO;

import java.util.List;

public interface ProblemService {

    void createProblem(Long memberPk, ProblemCreateRequestDTO problemCreateRequestDTO);

    /**
     * problemBankPk를 통해서 요약된 문제들을 불러옵니다
     * @param problemBankPk 문제 은행의 Pk
     * @return pk, title, writer
     */
    List<ProblemSummaryResponseDTO> getAllProblemSummary(Long problemBankPk);

    /**
     * 받은 문제은행Pk들을 조회 후 최대 100문제까지 불러옵니다.
     * 문제는 랜덤, 보기는 순서대로 나옵니다.
     * @param problemGetAllRequestDTO 문제 은행의 pk들과 최대 문제 수
     * @return 문제들의 전반적인 정보와 문제의 보기들(ProblemOption)
     */
    List<ProblemResponseDTO> getAllProblem(ProblemGetAllRequestDTO problemGetAllRequestDTO);

    /**
     * 수정용으로 제작됐습니다
     * @param problemPk
     * @return
     */
    ProblemUpdateResponseDTO getProblem(Long problemPk);

    /**
     * 문제와 보기들을 한 번에 받아 처리합니다.
     * @param problemUpdateRequestDTO 변경될 내용
     * @param memberPk 작성자 혹은 방장인지 확인
     */
    void updateProblem(ProblemUpdateRequestDTO problemUpdateRequestDTO,Long memberPk);

    /**
     * 권한을 확인 후 삭제합니다.
     * @param problemPk 삭제될 문제
     * @param memberPk 작성자 혹은 방장인지 확인
     */

    void deleteProblem(Long problemPk, Long memberPk);
}
