package com.als.SMore.domain.repository;

import com.als.SMore.domain.entity.Calender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalenderRepository extends JpaRepository<Calender, Long> {
}
