package com.als.SMore.domain.repository;

import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.StudyBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository

public interface StudyBoardRepository extends JpaRepository<StudyBoard, Long> {
    //게시물 대소문자 구분없이 수정날짜로 정렬
    @Query("SELECT sb FROM StudyBoard sb WHERE LOWER(sb.adTitle) LIKE LOWER(CONCAT('%', :keyword, '%')) ORDER BY sb.modifyDate DESC")
    Page<StudyBoard> searchByAdTitle(@Param("keyword") String keyword, Pageable pageable);

    Page<StudyBoard> findByAdTitleContainingIgnoreCaseOrderByModifyDateDesc(String keyword, Pageable pageable);
    StudyBoard findByAdTitleContainingIgnoreCaseOrderByModifyDateDesc(Study study);
}
