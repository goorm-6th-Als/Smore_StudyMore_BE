package com.als.SMore.domain.repository;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {

    boolean existsByStudyAndMember(Study study, Member member);
    Optional<StudyMember> findByMemberAndStudy(Member member, Study study);

    List<StudyMember> findByMember_MemberPkAndRole(Long memberPk,String role);
    Long countAllByStudy_StudyPk(Long studyPk);
    Optional<StudyMember> deleteStudyMemberByStudy_StudyPkAndMember_MemberPk (Long studyPk, Long memberPk);

    // 스터디 장 확인
    boolean existsByStudyStudyPkAndMemberMemberPkAndRole(Long studyPk, Long memberPk, String role);
    // 스터디 삭제 시 인원 체크
    long countByStudyStudyPk(Long studyPk);
    long countByStudyStudyPkAndRole(Long studyPk, String admin);
}
