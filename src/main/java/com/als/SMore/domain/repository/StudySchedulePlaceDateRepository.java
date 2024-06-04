package com.als.SMore.domain.repository;

import com.als.SMore.domain.entity.StudySchedulePlaceDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudySchedulePlaceDateRepository extends JpaRepository<StudySchedulePlaceDate, Long> {
}
