package com.als.SMore.domain.repository;

import com.als.SMore.domain.entity.Problem;
import com.als.SMore.domain.entity.ProblemOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemOptionsRepository extends JpaRepository<ProblemOptions, Long> {
    List<ProblemOptions> findAllByProblemOrderByOptionsNum(Problem problem);
}
