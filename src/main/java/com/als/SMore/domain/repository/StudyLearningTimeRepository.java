package com.als.SMore.domain.repository;

import com.als.SMore.domain.entity.AttendanceCheck;
import com.als.SMore.domain.entity.StudyLearningTime;
import com.als.SMore.domain.entity.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudyLearningTimeRepository extends JpaRepository<StudyLearningTime,Long> {

    Optional<StudyLearningTime> findByStudyMemberAndLearningDate(StudyMember studyMember, LocalDate learningDate);
    List<StudyLearningTime> findByStudyMemberAndLearningDateBetween(StudyMember studyMemberPk, LocalDate Start, LocalDate End);
}
