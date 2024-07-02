package com.als.SMore.domain.repository;

import com.als.SMore.domain.entity.Calendar;
import com.als.SMore.domain.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    List<Calendar> findByStudy(Study study);
}
