package com.als.SMore.domain.repository;

import com.als.SMore.domain.entity.AttendanceCheck;
import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceCheckRepository extends JpaRepository<AttendanceCheck, Long> {
    Optional<AttendanceCheck> findTopByMemberAndStudyOrderByAttendanceCheckPkDesc(Member member, Study study);

}
