package com.als.SMore.repository;

import com.als.SMore.entity.StudyProblemBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyProblemBankRepository extends JpaRepository<StudyProblemBank, Long> {
}
