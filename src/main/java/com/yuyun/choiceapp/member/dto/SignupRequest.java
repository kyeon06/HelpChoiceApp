package com.yuyun.choiceapp.member.dto;

import com.yuyun.choiceapp.member.entity.Authority;
import com.yuyun.choiceapp.member.entity.Member;
import com.yuyun.choiceapp.member.entity.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    private String email;
    private String password1;
    private String password2;
    private String username;
    private String nickname;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password1))
                .username(username)
                .nickname(nickname)
                .authority(Authority.ROLE_USER)
                .status(MemberStatus.PENDING)
                .build();
    }
}
