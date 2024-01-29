package com.yuyun.choiceapp.member.dto;

import com.yuyun.choiceapp.member.entity.Authority;
import com.yuyun.choiceapp.member.entity.Member;
import com.yuyun.choiceapp.member.entity.MemberStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 주소를 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
    private String password1;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
    private String password2;

    @NotBlank(message = "영문, 숫자를 포함하여 5~10자 이내로 입력해주세요.")
    @Size(min = 5, max = 10, message = "영문, 숫자를 포함하여 5~10자 이내로 입력해주세요.")
    private String username;

    @NotBlank(message = "한글/영문/숫자를 이용한 닉네임을 입력해주세요.")
    private String nickname;

    public Member toMember(String encodedPw, String authCode) {
        return Member.builder()
                .email(email)
                .password(encodedPw)
                .username(username)
                .nickname(nickname)
                .authority(Authority.ROLE_USER)
                .status(MemberStatus.PENDING)
                .authCode(authCode)
                .build();
    }
}
