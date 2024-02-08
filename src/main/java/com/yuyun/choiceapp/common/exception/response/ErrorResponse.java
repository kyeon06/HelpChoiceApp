package com.yuyun.choiceapp.common.exception.response;

import com.yuyun.choiceapp.member.exception.status.ExceptionStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ErrorResponse {
    private int status;
    private HttpStatus code;
    private String message;

    public ErrorResponse(ExceptionStatus exceptionStatus) {
        this.status = exceptionStatus.getStatus();
        this.code = exceptionStatus.getHttpStatus();
        this.message = exceptionStatus.getMessage();
    }
}
