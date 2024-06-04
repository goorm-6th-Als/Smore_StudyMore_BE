package com.als.SMore.repository;

import com.als.SMore.entity.ProblemOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemOptionsRepository extends JpaRepository<ProblemOptions, Long> {
}
