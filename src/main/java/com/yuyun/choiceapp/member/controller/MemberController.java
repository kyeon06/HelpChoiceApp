package com.yuyun.choiceapp.member.controller;

import com.yuyun.choiceapp.common.exception.response.ErrorResponse;
import com.yuyun.choiceapp.member.dto.*;
import com.yuyun.choiceapp.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.UnsupportedEncodingException;

@Slf4j
@Tag(name = "Member", description = "Member API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

    @Value("${yuyun.clientUrl}")
    private String clientUrl;

    private final MemberService memberService;

    @PostMapping("/members/email/check")
    @Operation(summary = "이메일 중복확인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이메일 사용가능"),
            @ApiResponse(responseCode = "400", description = "유효성 검사 실패",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "중복된 이메일 존재",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity checkEmail(@Valid @RequestBody DuplicateRequest duplicateRequest, BindingResult bindingResult) {
        memberService.checkEmail(duplicateRequest.getEmail(), bindingResult);
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

    @PostMapping("/members/username/check")
    @Operation(summary = "USERNAME 중복확인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "USERNAME 사용 가능"),
            @ApiResponse(responseCode = "400", description = "유효성 검사 실패",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "중복된 USERNAME 존재",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity checkUsername(@Valid @RequestBody DuplicateRequest duplicateRequest, BindingResult bindingResult) {
        memberService.checkUsername(duplicateRequest.getUsername(), bindingResult);
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

    @PostMapping("/members/nickname/check")
    @Operation(summary = "NICKNAME 중복확인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "NICKNAME 사용 가능"),
            @ApiResponse(responseCode = "400", description = "유효성 검사 실패",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "중복된 NICKNAME 존재",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity checkNickname(@Valid @RequestBody DuplicateRequest duplicateRequest, BindingResult bindingResult) {
        memberService.checkNickname(duplicateRequest.getNickname(), bindingResult);
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공",
                    content = {@Content(schema = @Schema(implementation = SignupResponse.class))}),
            @ApiResponse(responseCode = "400", description = "회원가입 실패",
                    content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public ResponseEntity<SignupResponse> signup(@Valid @RequestBody SignupRequest request, BindingResult bindingResult) throws MessagingException, UnsupportedEncodingException {
        SignupResponse response = memberService.signup(request, bindingResult);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/members/{memberId}/verify")
    @Operation(summary = "이메일 인증")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증성공"),
            @ApiResponse(responseCode = "404", description = "인증실패, 해당 ID의 회원을 찾을 수 없음")
    })
    public RedirectView verifyEmail(@PathVariable(name = "memberId") long memberId, @RequestParam(name = "authCode") String authCode) {
        memberService.verifyEmail(memberId, authCode);

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(clientUrl + "/signin/verify");
        return redirectView;
    }

    @PostMapping("/login")
    @Operation(summary = "로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "400", description = "비밀번호 불일치"),
            @ApiResponse(responseCode = "404", description = "해당 USERNAME의 회원을 찾을 수 없음")
    })
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(memberService.login(request));
    }

    @PostMapping("/token/refresh")
    @Operation(summary = "토큰 재발급")
    public ResponseEntity<TokenDto> tokenRefresh(@RequestBody TokenRefreshRequest tokenRefreshRequest) {
        return ResponseEntity.ok(memberService.tokenRefresh(tokenRefreshRequest));
    }
}