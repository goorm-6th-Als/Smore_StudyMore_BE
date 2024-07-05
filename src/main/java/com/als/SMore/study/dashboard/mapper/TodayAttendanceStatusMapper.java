package com.als.SMore.study.dashboard.mapper;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.study.dashboard.DTO.TodayAttendanceStatusDTO;
import java.time.Duration;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class TodayAttendanceStatusMapper {

    public static TodayAttendanceStatusDTO toDTO(Member member, String attendanceStatus, LocalDateTime attendanceDate, String timeAgo) {
        return TodayAttendanceStatusDTO.builder()
                .memberPk(member.getMemberPk())
                .nickname(member.getNickName())
                .profileImg(member.getProfileImg()) // 프로필 이미지 추가
                .attendanceStatus(attendanceStatus)
                .attendanceDate(attendanceDate)
                .timeAgo(timeAgo)
                .build();
    }

    public static String calculateTimeAgo(LocalDateTime attendanceDate) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(attendanceDate, now);

        long totalMinutes = duration.toMinutes();
        long hours = totalMinutes / 60;
        long minutes = totalMinutes % 60;

        return hours > 0 ? hours + "시간 전 출석" : minutes + "분 전 출석";
    }
}
