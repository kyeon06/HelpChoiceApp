package com.yuyun.choiceapp.member.exception;

import com.yuyun.choiceapp.member.exception.status.ExceptionStatus;
import lombok.Getter;

@Getter
public class MemberException extends RuntimeException {
    private final ExceptionStatus status;

    public MemberException(ExceptionStatus status) {
        super(status.getMessage());
        this.status = status;
    }

}
