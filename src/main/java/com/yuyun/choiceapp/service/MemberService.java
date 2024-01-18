package com.yuyun.choiceapp.service;

import com.yuyun.choiceapp.dto.SignupRequest;
import com.yuyun.choiceapp.dto.SignupResponse;
import com.yuyun.choiceapp.entity.Member;
import com.yuyun.choiceapp.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignupResponse signup(SignupRequest request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }
        if (memberRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("이미 사용중인 ID 입니다");
        }
        if (memberRepository.existsByNickname(request.getNickname())) {
            throw new RuntimeException("사용할 수 없는 nickname 입니다");
        }

        String password1 = request.getPassword1();
        String password2 = request.getPassword2();
        if (!password1.equals(password2)) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        Member member = request.toMember(passwordEncoder);
        return SignupResponse.of(memberRepository.save(member));
    }
}