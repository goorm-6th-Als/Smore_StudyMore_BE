package com.als.SMore.domain.repository;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.StudyProblemBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyProblemBankRepository extends JpaRepository<StudyProblemBank, Long> {
    List<StudyProblemBank> findByStudyOrderByStudyProblemBankPkDesc(Study study);
    List<StudyProblemBank> findByMemberAndStudy(Member member, Study study);
    long countByStudy(Study study);
}
