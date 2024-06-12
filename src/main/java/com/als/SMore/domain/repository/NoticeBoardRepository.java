package com.als.SMore.domain.repository;

import com.als.SMore.domain.entity.NoticeBoard;
import com.als.SMore.domain.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Long> {
    List<NoticeBoard> findAllByStudy(Study study);

    Optional<NoticeBoard> findByNoticeBoardPkAndStudy(Long NoticeBoardPk, Study study);

}
