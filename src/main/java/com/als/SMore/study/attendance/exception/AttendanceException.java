package com.als.SMore.study.attendance.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AttendanceException extends RuntimeException{
    AttendanceErrorCode attendanceExceptionCode;
}
