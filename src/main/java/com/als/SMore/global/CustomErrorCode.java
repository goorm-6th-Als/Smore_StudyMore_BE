package com.als.SMore.global;

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
    NOT_AUTHORIZED_REQUEST(HttpStatus.UNAUTHORIZED, "공지사항은 스터디장만 생성, 수정, 삭제 할 수 있습니다.");




    //attendance
    NOT_FOUND_USER(HttpStatus.NOT_FOUND,"없는 유저 입니다."),
    NOT_FOUND_STUDY(HttpStatus.NOT_FOUND, "없는 스터디 입니다"),
    NOT_FOUND_USER_OR_STUDY(HttpStatus.NOT_FOUND, "없는 유저 또는 스터디 입니다"),
    INVALID_VALUE(HttpStatus.BAD_REQUEST, "유효하지 않은 값입니다"),

    //problem
    NOT_FOUND_PROBLEM_BANK(HttpStatus.NOT_FOUND, "없는 문제은행 입니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "방장이나 작성자만 접근 가능합니다"),
    INVALID_TITLE_BANK_NAME(HttpStatus.BAD_REQUEST, "문제은행 이름은 공백 포함 1자 이상 30자 이하로 해주세요."),
    PROBLEM_BANK_COUNT_OVER_LIMIT(HttpStatus.BAD_REQUEST, "문제은행은 최대 30개까지 생성 가능합니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
