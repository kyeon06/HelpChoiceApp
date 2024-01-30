package com.yuyun.choiceapp.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private String fieldName;
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }
}
