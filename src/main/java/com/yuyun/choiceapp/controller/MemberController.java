package com.yuyun.choiceapp.controller;

import com.yuyun.choiceapp.dto.SignupRequest;
import com.yuyun.choiceapp.dto.SignupResponse;
import com.yuyun.choiceapp.dto.TokenDto;
import com.yuyun.choiceapp.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest request) {
        return ResponseEntity.ok(memberService.signup(request));
    }
}