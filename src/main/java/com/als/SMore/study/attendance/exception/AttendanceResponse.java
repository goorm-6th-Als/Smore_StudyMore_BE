package com.als.SMore.study.attendance.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class AttendanceResponse {
    private final int status;
    private final String message;

    public static ResponseEntity<AttendanceResponse> toResponse(AttendanceErrorCode errorCode) {
        AttendanceResponse response = AttendanceResponse.builder()
                .status(errorCode.getStatus().value())
                .message(errorCode.getMessage())
                .build();
        return new ResponseEntity<>(response, errorCode.getStatus());
    }
}
