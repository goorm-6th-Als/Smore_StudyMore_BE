package com.als.SMore.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {
    CustomErrorCode(HttpStatus.BAD_REQUEST,"이미 존재하는 ID입니다."),
    INVALID_NICKNAME(HttpStatus.BAD_REQUEST,"이미 존재하는 닉네임입니다."),
    UNKNOWN_PASSWORD(HttpStatus.BAD_REQUEST ,"기존 비밀번호를 확인해주세요."),
    ERROR_PASSWORD(HttpStatus.BAD_REQUEST,"비밀번호가 일치하지 않습니다."),
    ERROR_USER(HttpStatus.BAD_REQUEST,"해당하는 유저가 없습니다."),
    NOT_SAME_PASSWORD(HttpStatus.BAD_REQUEST,"동일한 비밀번호로 변경할 수 없습니다."),
    INVALID_USERID(HttpStatus.BAD_REQUEST,"이미 존재하는 아이디입니다"),


    //notice
    NOT_EXIST_PAGE(HttpStatus.NOT_FOUND, "존재하지 않는 게시물 입니다."),
    INVALID_TITLE_NAME(HttpStatus.BAD_REQUEST, "제목은 공백 포함 1자 이상 30자 이하로 해주세요."),
    NOT_AUTHORIZED_REQUEST(HttpStatus.UNAUTHORIZED, "공지사항은 스터디장만 생성, 수정, 삭제 할 수 있습니다."),




    //attendance
    NOT_FOUND_USER(HttpStatus.NOT_FOUND,"없는 유저 입니다."),
    NOT_FOUND_STUDY(HttpStatus.NOT_FOUND, "없는 스터디 입니다"),
    NOT_FOUND_USER_OR_STUDY(HttpStatus.NOT_FOUND, "없는 유저 또는 스터디 입니다"),
    INVALID_VALUE(HttpStatus.BAD_REQUEST, "유효하지 않은 값입니다"),

    //problem
    NOT_FOUND_PROBLEM_BANK(HttpStatus.NOT_FOUND, "없는 문제은행 입니다."),
    NOT_FOUND_PROBLEM(HttpStatus.NOT_FOUND, "없는 문제 입니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "방장이나 작성자만 접근 가능합니다"),
    INVALID_TITLE_BANK_NAME(HttpStatus.BAD_REQUEST, "문제은행 이름은 공백 포함 1자 이상 30자 이하로 해주세요."),
    PROBLEM_BANK_COUNT_OVER_LIMIT(HttpStatus.BAD_REQUEST, "문제은행은 최대 30개까지 생성 가능합니다."),

    //Study Todo
    INVALID_STATUS(HttpStatus.BAD_REQUEST, "올바른 Todo 상태가 아닙니다"),
    NOT_FOUND_TODO(HttpStatus.NOT_FOUND,"존재하지 않는 Todo 입니다."),
    NOT_STUDY_MEMBER(HttpStatus.UNAUTHORIZED, "스터디 멤버가 아닙니다."),

    //Study Management
    MAX_STUDY_PARTICIPATION_EXCEEDED(HttpStatus.BAD_REQUEST,"스터디는 5개까지 참가 가능합니다."),
    NOT_STUDY_ADMIN(HttpStatus.UNAUTHORIZED, "스터디 장이 아닙니다."),
    STILL_EXISTS_MEMBERS(HttpStatus.BAD_REQUEST, "스터디에 다른 멤버가 존재합니다."),
    NOT_AUTHORIZED_REQUEST_MEMBER(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),

    //Calender
    NOT_FOUND_CALENDER(HttpStatus.NOT_FOUND, "일정표를 찾을 수 없습니다."),
    INVALID_ACCESS_CALENDER(HttpStatus.BAD_REQUEST, "잘못된 접근 입니다"),
    UNAUTHORIZED_ACCESS_CALENDER(HttpStatus.UNAUTHORIZED, "방장이나 작성자만 접근 가능합니다"),

    //StudyBoard
    NOT_FOUND_STUDY_BOARD(HttpStatus.NOT_FOUND, "존재하지 않는 스터디 게시물 입니다."),
    NOT_FOUND_STUDY_DETAIL(HttpStatus.NOT_FOUND, "존재하지 않는 studyDetail 입니다."),
    //StudyEnter
    ALREADY_MEMBER(HttpStatus.BAD_REQUEST, "이미 가입한 스터디입니다."),
    ALREADY_APPLIED(HttpStatus.BAD_REQUEST, "이미 신청한 스터디입니다."),

    //aop 접근 error
    NOT_FOUND_STUDY_PK(HttpStatus.UNAUTHORIZED,"해당한 권한이 없습니다"),
    NOT_FOUND_ROLE(HttpStatus.UNAUTHORIZED,"접근이 허용되지 않았습니다"),

    //aop 접근 새로운 accessToken 발급
    JWT_AUTH_CODE(HttpStatus.BAD_REQUEST,"새로운 권한이 발급되었습니다");

    private final HttpStatus httpStatus;
    private final String message;
}
