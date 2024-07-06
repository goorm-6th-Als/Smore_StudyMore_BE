package com.als.SMore.study.dashboard.mapper;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.study.dashboard.DTO.MonthlyAttendanceStatusDTO;
import java.time.LocalDate;

public class MonthlyAttendanceStatusMapper {
    public static MonthlyAttendanceStatusDTO toDTO(Member member, String attendanceStatus, LocalDate attendanceDate) {
        return MonthlyAttendanceStatusDTO.builder()
                .memberPk(member.getMemberPk())
                .attendanceStatus(attendanceStatus)
                .attendanceDate(attendanceDate)
                .build();
    }
}

