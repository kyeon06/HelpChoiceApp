package com.yuyun.choiceapp.member.dto;

import com.yuyun.choiceapp.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignupResponse {
    private String username;
    private String email;

    public static SignupResponse of(Member member) {
        return new SignupResponse(member.getUsername(), member.getEmail());
    }
}