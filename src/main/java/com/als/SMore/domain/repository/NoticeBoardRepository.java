package com.als.SMore.domain.repository;

import com.als.SMore.domain.entity.NoticeBoard;
import com.als.SMore.domain.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Long> {
    List<NoticeBoard> findAllByStudy(Study study);

    NoticeBoard findByNoticeBoardPk(Long NoticeBoardPk);

}
