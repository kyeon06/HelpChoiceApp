package com.yuyun.choiceapp.member.exception.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionStatus {

    EXIST_MEMBER_USERNAME(HttpStatus.CONFLICT, "이미 사용중인 ID 입니다"),
    EXIST_MEMBER_NICKNAME(HttpStatus.CONFLICT, "이미 사용중인 NICKNAME 입니다"),
    EXIST_MEMBER_EMAIL(HttpStatus.CONFLICT, "이미 사용중인 EMAIL 입니다"),
    NOT_EXIST_MEMBER(HttpStatus.NOT_FOUND, "유저가 존재하지 않습니다."),
    EXIST_MEMBER(HttpStatus.CONFLICT, "이미 가입되어 있는 유저입니다"),
    NOT_CORRECT_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    LOGIN_ACCESS_DENIED(HttpStatus.BAD_REQUEST, "로그인 할 수 없습니다. 먼저 이메일 인증을 완료해주세요."),
    NOT_VALID_AUTH_CODE(HttpStatus.BAD_REQUEST, "인증코드가 유효하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
