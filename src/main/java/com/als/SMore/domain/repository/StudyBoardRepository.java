package com.als.SMore.domain.repository;

import com.als.SMore.domain.entity.StudyBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyBoardRepository extends JpaRepository<StudyBoard, Long> {
}
