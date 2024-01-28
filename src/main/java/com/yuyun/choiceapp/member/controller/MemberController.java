package com.yuyun.choiceapp.member.controller;

import com.yuyun.choiceapp.member.dto.*;
import com.yuyun.choiceapp.member.service.MemberService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest request) throws MessagingException, UnsupportedEncodingException {
        return ResponseEntity.ok(memberService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(memberService.login(request));
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<TokenDto> tokenRefresh(@RequestBody TokenRefreshRequest tokenRefreshRequest) {
        return ResponseEntity.ok(memberService.tokenRefresh(tokenRefreshRequest));
    }
}