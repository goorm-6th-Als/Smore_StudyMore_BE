package com.als.SMore.domain.repository;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudyRepository extends JpaRepository<Study, Long> {
    @Query("SELECT s.member FROM Study s WHERE s.studyPk = :studyPk")
    Member findMemberByStudyPk(@Param("studyPk") Long studyPk);

}
