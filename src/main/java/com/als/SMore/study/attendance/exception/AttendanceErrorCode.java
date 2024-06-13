package com.als.SMore.study.attendance.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AttendanceErrorCode {
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST,"없는 유저 입니다."),
    NOT_FOUND_STUDY(HttpStatus.BAD_REQUEST, "없는 스터디 입니다");



    private final HttpStatus status;
    private final String message;

}
