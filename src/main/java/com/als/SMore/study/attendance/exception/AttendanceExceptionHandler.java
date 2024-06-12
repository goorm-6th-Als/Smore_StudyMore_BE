package com.als.SMore.study.attendance.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AttendanceExceptionHandler {
    @ExceptionHandler(AttendanceException.class)
    protected ResponseEntity<AttendanceResponse> handleAttendanceException(AttendanceException e) {
        return AttendanceResponse.toResponse(e.getAttendanceExceptionCode());
    }
}