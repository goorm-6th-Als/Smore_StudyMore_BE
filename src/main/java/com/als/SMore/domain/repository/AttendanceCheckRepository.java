package com.als.SMore.domain.repository;

import com.als.SMore.domain.entity.AttendanceCheck;
import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.entity.Study;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceCheckRepository extends JpaRepository<AttendanceCheck, Long> {
    Optional<AttendanceCheck> findTopByMemberAndStudyOrderByAttendanceCheckPkDesc(Member member, Study study);

    // 출석체크 용
    boolean existsByMemberAndStudyAndAttendanceDateBetween(Member member, Study study, LocalDateTime startDateTime, LocalDateTime endDateTime);
    // 출석 먼저 한 순으로 정렬
    Optional<AttendanceCheck> findFirstByMemberAndStudyAndAttendanceDateBetweenOrderByAttendanceDateAsc(Member member, Study study, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
