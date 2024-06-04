package com.als.SMore.repository;

import com.als.SMore.entity.AttendanceCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceCheckRepository extends JpaRepository<AttendanceCheck, Long> {
}
