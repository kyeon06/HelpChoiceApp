package com.yuyun.choiceapp.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class DuplicateRequest {

    @Pattern(regexp = "^[a-z0-9]{5,10}$", message = "영문, 숫자를 포함하여 5~10자 이내로 입력해주세요.")
    private String username;

    @Pattern(regexp = "^[가-힣a-zA-Z0-9]*$", message = "한글, 영문, 숫자를 이용한 닉네임을 입력해주세요.")
    private String nickname;

    @Email(message = "올바른 이메일 주소를 입력해주세요.")
    private String email;
}
