package com.als.SMore.domain.repository;

import com.als.SMore.domain.entity.StudyEnterMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudyEnterMemberRepository extends JpaRepository<StudyEnterMember,Long> {
    Optional<StudyEnterMember> deleteStudyEnterMemberByMember_UserIdAndStudy_StudyPk (String userId,Long studyPk);
    Optional<StudyEnterMember> findStudyEnterMemberByMember_UserIdAndStudy_StudyPk (String userId,Long studyPk);
    List<StudyEnterMember> findAllByStudy_StudyPk(Long studyPk);
}
