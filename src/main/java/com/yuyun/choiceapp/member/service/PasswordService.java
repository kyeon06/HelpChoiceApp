package com.yuyun.choiceapp.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordService {

    private final PasswordEncoder passwordEncoder;

    public String encode(String password1, String password2) {
        if (!password1.equals(password2)) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return passwordEncoder.encode(password1);
    }

    public boolean matches(String basicPw, String encodedPw) {
        if (!passwordEncoder.matches(basicPw, encodedPw)) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다");
        }

        return true;
    }
}
