package com.yuyun.choiceapp.member.exception.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionStatus {

    EXIST_MEMBER_USERNAME(409, HttpStatus.CONFLICT, "이미 사용중인 ID 입니다"),
    EXIST_MEMBER_NICKNAME(409, HttpStatus.CONFLICT, "이미 사용중인 NICKNAME 입니다"),
    EXIST_MEMBER_EMAIL(409, HttpStatus.CONFLICT, "이미 사용중인 EMAIL 입니다"),
    EXIST_MEMBER(409, HttpStatus.CONFLICT, "이미 가입되어 있는 유저입니다"),

    NOT_EXIST_MEMBER(404, HttpStatus.NOT_FOUND, "유저가 존재하지 않습니다."),

    NOT_CORRECT_PASSWORD(400, HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    LOGIN_ACCESS_DENIED(400, HttpStatus.BAD_REQUEST, "로그인 할 수 없습니다. 먼저 이메일 인증을 완료해주세요."),
    NOT_VALID_AUTH_CODE(400, HttpStatus.BAD_REQUEST, "인증코드가 유효하지 않습니다."),

    FAIL_VALID_USERNAME(400, HttpStatus.BAD_REQUEST, "영문, 숫자를 포함하여 5~10자 이내로 입력해주세요."),
    FAIL_VALID_NICKNAME(400, HttpStatus.BAD_REQUEST, "한글, 영문, 숫자를 이용한 닉네임을 입력해주세요."),
    FAIL_VALID_EMAIL(400, HttpStatus.BAD_REQUEST, "올바른 이메일 주소를 입력해주세요."),
    FAIL_VALID_PASSWORD(400, HttpStatus.BAD_REQUEST, "비밀번호는 8자 이상 20자 이하로 입력해주세요."),

    FAIL_SIGNUP(400, HttpStatus.BAD_REQUEST, "회원가입 실패, 다시 시도해주세요.");

    private final int status;
    private final HttpStatus httpStatus;
    private final String message;
}
