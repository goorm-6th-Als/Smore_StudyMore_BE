package com.als.SMore.domain.repository;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.StudyEnterMember;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyEnterMemberRepository extends JpaRepository<StudyEnterMember,Long> {
    // 스터디 가입 신청 관련
    List<StudyEnterMember> findByStudyStudyPk(Long studyPk);
    List<StudyEnterMember> findByMemberMemberPk(Long memberPk);
  
    void deleteStudyEnterMemberByMember_UserIdAndStudy_StudyPk (String userId,Long studyPk);
    Optional<StudyEnterMember> findStudyEnterMemberByMember_UserIdAndStudy_StudyPk (String userId,Long studyPk);
    List<StudyEnterMember> findAllByStudy_StudyPk(Long studyPk);

    // 스터디에 신청했는지 체크
    boolean existsByStudyAndMember(Study study, Member member);
}
