package com.als.SMore.domain.repository;

import com.als.SMore.domain.entity.StudyProblemBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyProblemBankRepository extends JpaRepository<StudyProblemBank, Long> {
}
