package com.als.SMore.study.dashboard.mapper;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.study.dashboard.DTO.AttendanceStatusDTO;
import java.time.Duration;
import java.time.LocalDateTime;

public class AttendanceStatusMapper {
    public static AttendanceStatusDTO toDTO(Member member, String attendanceStatus, LocalDateTime attendanceDate) {
        return AttendanceStatusDTO.builder()
                .memberPk(member.getMemberPk())
                .nickname(member.getNickName())
                .attendanceStatus(attendanceStatus)
                .attendanceDate(attendanceDate)
                .build();
    }

    public static AttendanceStatusDTO fromEntity(Member member, String attendanceStatus, LocalDateTime attendanceDate, String timeAgo) {
        return AttendanceStatusDTO.builder()
                .memberPk(member.getMemberPk())
                .nickname(member.getNickName())
                .attendanceStatus(attendanceStatus)
                .attendanceDate(attendanceDate)
                .timeAgo(timeAgo)
                .build();
    }

    // 추가된 메서드
    public static String calculateTimeAgo(LocalDateTime attendanceDate) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(attendanceDate, now);

        long totalMinutes = duration.toMinutes();
        long hours = totalMinutes / 60;
        long minutes = totalMinutes % 60;

        return hours > 0 ? hours + "시간 전 출석" : minutes + "분 전 출석";
    }
}
