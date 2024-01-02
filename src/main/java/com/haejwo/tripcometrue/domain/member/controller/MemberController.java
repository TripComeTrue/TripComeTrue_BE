package com.haejwo.tripcometrue.domain.member.controller;

import com.haejwo.tripcometrue.domain.member.request.SignUpRequest;
import com.haejwo.tripcometrue.domain.member.response.SignUpResponse;
import com.haejwo.tripcometrue.domain.member.service.MemberService;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDTO<SignUpResponse>> signup(
        @Valid @RequestBody SignUpRequest signUpRequest) {
        SignUpResponse signupResponse = memberService.signup(signUpRequest);
        ResponseDTO<SignUpResponse> response = ResponseDTO.okWithData(signupResponse);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}