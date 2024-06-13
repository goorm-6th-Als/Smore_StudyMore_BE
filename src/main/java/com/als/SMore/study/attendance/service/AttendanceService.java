package com.als.SMore.study.attendance.service;

import java.time.LocalDateTime;

public interface AttendanceService {

    LocalDateTime attendanceStart(Long memberPk, Long StudyPk);
    Long attendanceEnd(Long memberPk, Long StudyPk);

}
