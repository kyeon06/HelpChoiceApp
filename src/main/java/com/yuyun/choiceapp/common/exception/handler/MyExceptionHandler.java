package com.yuyun.choiceapp.common.exception.handler;

import com.yuyun.choiceapp.common.exception.response.ErrorResponse;
import com.yuyun.choiceapp.member.exception.MemberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ErrorResponse> handleMemberException(MemberException ex) {
        log.error("handleMemberException", ex);
        ErrorResponse response = new ErrorResponse(ex.getStatus());
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(ex.getStatus().getStatus()));
    }

}
