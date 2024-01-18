package com.yuyun.choiceapp.service;

import com.yuyun.choiceapp.dto.LoginRequest;
import com.yuyun.choiceapp.dto.SignupRequest;
import com.yuyun.choiceapp.dto.SignupResponse;
import com.yuyun.choiceapp.dto.TokenDto;
import com.yuyun.choiceapp.entity.Member;
import com.yuyun.choiceapp.entity.RefreshToken;
import com.yuyun.choiceapp.jwt.TokenProvider;
import com.yuyun.choiceapp.repository.MemberRepository;
import com.yuyun.choiceapp.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

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

    @Transactional
    public TokenDto login(LoginRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = request.toAuthentication();

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        return tokenDto;
    }
}