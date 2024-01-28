package com.yuyun.choiceapp.member.controller;

import com.yuyun.choiceapp.member.dto.*;
import com.yuyun.choiceapp.member.service.MemberService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.UnsupportedEncodingException;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

    @Value("${yuyun.clientUrl}")
    private String clientUrl;

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest request) throws MessagingException, UnsupportedEncodingException {
        return ResponseEntity.ok(memberService.signup(request));
    }

    @GetMapping("/members/{memberId}/verify")
    public RedirectView verifyEmail(@PathVariable(name = "memberId") long memberId, @RequestParam(name = "authCode") String authCode) {
        memberService.verifyEmail(memberId, authCode);

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(clientUrl + "/signin");
        return redirectView;
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