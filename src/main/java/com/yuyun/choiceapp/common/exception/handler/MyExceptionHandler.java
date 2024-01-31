package com.yuyun.choiceapp.common.exception.handler;

import com.yuyun.choiceapp.common.exception.response.ExceptionResponse;
import com.yuyun.choiceapp.member.exception.MemberException;
import com.yuyun.choiceapp.member.exception.status.ExceptionStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ExceptionResponse response = new ExceptionResponse(HttpStatus.BAD_REQUEST, e.getFieldError().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler({MemberException.class})
    public ResponseEntity<Object> memberException(MemberException e) {
        ExceptionStatus status = e.getStatus();
        ExceptionResponse response = new ExceptionResponse(status.getHttpStatus(), status.getMessage());
        return ResponseEntity.status(status.getHttpStatus()).body(response);
    }

}
