package com.als.SMore.domain.repository;

import com.als.SMore.domain.entity.ProblemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemTypeRepository extends JpaRepository<ProblemType, Long> {
}
