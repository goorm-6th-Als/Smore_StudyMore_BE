package com.als.SMore.domain.repository;

import com.als.SMore.domain.entity.StudyEnterMember;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyEnterMemberRepository extends JpaRepository<StudyEnterMember,Long> {
    List<StudyEnterMember> findByStudyStudyPk(Long studyPk);
    List<StudyEnterMember> findByMemberMemberPk(Long memberPk);
}
