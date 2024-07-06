package com.als.SMore.domain.repository;

import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.StudyDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudyDetailRepository extends JpaRepository<StudyDetail, Long> {
    StudyDetail findByStudy(Study updatedStudy);
    Optional<StudyDetail> findByStudy_StudyPk (Long studyPk);

    Optional<StudyDetail> findByStudyPk(Long studyPk);

    Optional<StudyDetail> findByStudyStudyPk(Long studyPk);
}
