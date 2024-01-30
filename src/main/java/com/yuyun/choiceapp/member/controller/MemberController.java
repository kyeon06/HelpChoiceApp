package com.yuyun.choiceapp.member.controller;

import com.yuyun.choiceapp.member.dto.*;
import com.yuyun.choiceapp.member.exception.MemberException;
import com.yuyun.choiceapp.member.exception.status.ExceptionStatus;
import com.yuyun.choiceapp.member.service.MemberService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.UnsupportedEncodingException;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

    @Value("${yuyun.clientUrl}")
    private String clientUrl;

    private final MemberService memberService;

    @PostMapping("/members/email/check")
    public ResponseEntity checkEmail(@Valid @RequestBody DuplicateRequest duplicateRequest, BindingResult bindingResult) {
        // 에러처리
        if (bindingResult.hasErrors()) {
            ErrorResponse errorResponse = new ErrorResponse();
            bindingResult.getAllErrors().forEach(objectError -> {
                FieldError field = (FieldError) objectError;
                String message = objectError.getDefaultMessage();
                errorResponse.setFieldName(field.getField());
                errorResponse.setMessage(message);
            });
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        if (memberService.checkEmail(duplicateRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MemberException(ExceptionStatus.EXIST_MEMBER_EMAIL));
        }
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

    @PostMapping("/members/username/check")
    public ResponseEntity checkUsername(@Valid @RequestBody DuplicateRequest duplicateRequest, BindingResult bindingResult) {
        // 에러처리
        if (bindingResult.hasErrors()) {
            ErrorResponse errorResponse = new ErrorResponse();
            bindingResult.getAllErrors().forEach(objectError -> {
                FieldError field = (FieldError) objectError;
                String message = objectError.getDefaultMessage();
                errorResponse.setFieldName(field.getField());
                errorResponse.setMessage(message);
            });
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        if (memberService.checkUsername(duplicateRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MemberException(ExceptionStatus.EXIST_MEMBER_USERNAME));
        }
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

    @PostMapping("/members/nickname/check")
    public ResponseEntity checkNickname(@Valid @RequestBody DuplicateRequest duplicateRequest, BindingResult bindingResult) {
        // 에러처리
        if (bindingResult.hasErrors()) {
            ErrorResponse errorResponse = new ErrorResponse();
            bindingResult.getAllErrors().forEach(objectError -> {
                FieldError field = (FieldError) objectError;
                String message = objectError.getDefaultMessage();
                errorResponse.setFieldName(field.getField());
                errorResponse.setMessage(message);
            });
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        if (memberService.checkNickname(duplicateRequest.getNickname())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MemberException(ExceptionStatus.EXIST_MEMBER_NICKNAME));
        }
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@Valid @RequestBody SignupRequest request, BindingResult bindingResult) throws MessagingException, UnsupportedEncodingException {

        // 에러처리
        if (bindingResult.hasErrors()) {
            ErrorResponse errorResponse = new ErrorResponse();
            bindingResult.getAllErrors().forEach(objectError -> {
                FieldError field = (FieldError) objectError;
                String message = objectError.getDefaultMessage();
                errorResponse.setFieldName(field.getField());
                errorResponse.setMessage(message);
            });
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
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