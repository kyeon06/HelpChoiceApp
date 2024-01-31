package com.yuyun.choiceapp.common.exception.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class ExceptionResponse {
    private final HttpStatus httpStatus;
    private final String message;
}
