package com.yuyun.choiceapp.member.service;

import com.yuyun.choiceapp.member.dto.*;
import com.yuyun.choiceapp.member.entity.Member;
import com.yuyun.choiceapp.member.entity.MemberStatus;
import com.yuyun.choiceapp.member.entity.RefreshToken;
import com.yuyun.choiceapp.jwt.TokenProvider;
import com.yuyun.choiceapp.member.repository.MemberRepository;
import com.yuyun.choiceapp.member.repository.RefreshTokenRepository;
import com.yuyun.choiceapp.util.RandomCodeCreator;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordService passwordService;
    private final MailService mailService;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    // 회원가입
    @Transactional
    public SignupResponse signup(SignupRequest request) throws MessagingException, UnsupportedEncodingException {
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }
        if (memberRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("이미 사용중인 ID 입니다");
        }
        if (memberRepository.existsByNickname(request.getNickname())) {
            throw new RuntimeException("사용할 수 없는 nickname 입니다");
        }

        String encodedPw = passwordService.encode(request.getPassword1(), request.getPassword2());

        RandomCodeCreator randomCodeCreator = new RandomCodeCreator();
        String authCode = randomCodeCreator.getRandomCode(10);

        Member member = request.toMember(encodedPw, authCode);
        Member savedMember = memberRepository.save(member);

        mailService.send(savedMember.getEmail(), savedMember.getId(), savedMember.getAuthCode());

        return SignupResponse.of(savedMember);
    }

    // 이메일 인증
    @Transactional
    public void verifyEmail(long memberId, String authCode) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        member = member.verify(authCode);
        memberRepository.save(member);
    }

    // 로그인
    @Transactional
    public TokenDto login(LoginRequest request) {
        Member member = memberRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));

        if (!member.getStatus().equals(MemberStatus.ACTIVE)) {
            throw new RuntimeException("로그인 할 수 없습니다. 먼저 이메일 인증을 완료해주세요.");
        }

        passwordService.matches(request.getPassword(), member.getPassword());

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

    // 토큰 재발급
    @Transactional
    public TokenDto tokenRefresh(TokenRefreshRequest tokenRefreshRequest) {
        if (!tokenProvider.validateToken(tokenRefreshRequest.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        Authentication authentication = tokenProvider.getAuthentication(tokenRefreshRequest.getAccessToken());

        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        if (!refreshToken.getValue().equals(tokenRefreshRequest.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        return tokenDto;
    }
}