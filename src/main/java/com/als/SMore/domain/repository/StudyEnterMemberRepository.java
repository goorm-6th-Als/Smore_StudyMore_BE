package com.als.SMore.domain.repository;

import com.als.SMore.domain.entity.StudyEnterMember;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudyEnterMemberRepository extends JpaRepository<StudyEnterMember,Long> {
    // 스터디 가입 신청 관련
    List<StudyEnterMember> findByStudyStudyPk(Long studyPk);
    List<StudyEnterMember> findByMemberMemberPk(Long memberPk);
  
    Optional<StudyEnterMember> deleteStudyEnterMemberByMember_UserIdAndStudy_StudyPk (String userId,Long studyPk);
    Optional<StudyEnterMember> findStudyEnterMemberByMember_UserIdAndStudy_StudyPk (String userId,Long studyPk);
    List<StudyEnterMember> findAllByStudy_StudyPk(Long studyPk);
}
