package com.als.SMore.domain.repository;

import com.als.SMore.domain.entity.Problem;
import com.als.SMore.domain.entity.StudyProblemBank;
import com.als.SMore.study.problem.DTO.response.problem.ProblemSummaryResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {

    @Query("SELECT new com.als.SMore.study.problem.DTO.response.problem.ProblemSummaryResponseDTO(p.problemPk, p.problemTitle, m.nickName) " +
            "FROM Problem p JOIN p.member m " +
            "WHERE p.studyProblemBank = :studyProblemBank")
    List<ProblemSummaryResponseDTO> findAllProblemSummaryByStudyProblem(StudyProblemBank studyProblemBank);

    List<Problem> findByStudyProblemBank(StudyProblemBank studyProblemBank);

}
