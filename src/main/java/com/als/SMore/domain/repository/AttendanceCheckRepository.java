package com.als.SMore.domain.repository;

import com.als.SMore.domain.entity.AttendanceCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceCheckRepository extends JpaRepository<AttendanceCheck, Long> {
}
