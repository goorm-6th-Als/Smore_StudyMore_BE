package com.als.SMore.repository;

import com.als.SMore.entity.StudyDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyDetailRepository extends JpaRepository<StudyDetail, Long> {
}
