package com.haejwo.tripcometrue.domain.member.controller;

import com.haejwo.tripcometrue.domain.member.dto.request.IntroductionRequestDto;
import com.haejwo.tripcometrue.domain.member.dto.request.NicknameRequestDto;
import com.haejwo.tripcometrue.domain.member.dto.request.PasswordRequestDto;
import com.haejwo.tripcometrue.domain.member.dto.request.ProfileImageRequestDto;
import com.haejwo.tripcometrue.domain.member.dto.request.SignUpRequestDto;
import com.haejwo.tripcometrue.domain.member.dto.response.IntroductionResponseDto;
import com.haejwo.tripcometrue.domain.member.dto.response.LoginResponseDto;
import com.haejwo.tripcometrue.domain.member.dto.response.MemberDetailResponseDto;
import com.haejwo.tripcometrue.domain.member.dto.response.NicknameResponseDto;
import com.haejwo.tripcometrue.domain.member.dto.response.ProfileImageResponseDto;
import com.haejwo.tripcometrue.domain.member.dto.response.SignUpResponseDto;
import com.haejwo.tripcometrue.domain.member.dto.response.TestUserResponseDto;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.member.service.MemberService;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDTO<SignUpResponseDto>> signup(
        @Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        SignUpResponseDto signUpResponseDto = memberService.signup(signUpRequestDto);
        ResponseDTO<SignUpResponseDto> response = ResponseDTO.okWithData(signUpResponseDto);
        return ResponseEntity
            .status(response.getCode())
            .body(response);
    }

    // Authenticated user 샘플테스트 코드입니다
    @GetMapping("/test/jwt")
    public ResponseEntity<ResponseDTO<TestUserResponseDto>> test(
        @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Member member = principalDetails.getMember();

        TestUserResponseDto testUserResponseDto = TestUserResponseDto.fromEntity(member);
        ResponseDTO<TestUserResponseDto> response = ResponseDTO.okWithData(testUserResponseDto);
        return ResponseEntity
            .status(response.getCode())
            .body(response);
    }

    @GetMapping("/check-duplicated-email")
    public ResponseEntity<ResponseDTO<Void>> checkDuplicateEmail(
        @RequestParam String email) {
        memberService.checkDuplicateEmail(email);
        ResponseDTO<Void> response = ResponseDTO.ok();
        return ResponseEntity
            .status(response.getCode())
            .body(response);
    }

    @GetMapping("/oauth2/info")
    public ResponseEntity<ResponseDTO<LoginResponseDto>> oauth2Test(
        @RequestParam String token, @RequestParam String email, @RequestParam String name) {
        LoginResponseDto loginResponseDto = new LoginResponseDto(email, name, token);

        ResponseDTO<LoginResponseDto> response = ResponseDTO.okWithData(loginResponseDto);
        return ResponseEntity
            .status(response.getCode())
            .body(response);
    }

    @PatchMapping("/change-password")
    public ResponseEntity<ResponseDTO<Void>> changePassword(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @Valid @RequestBody PasswordRequestDto passwordRequestDto) {

        memberService.changePassword(principalDetails, passwordRequestDto);
        ResponseDTO<Void> response = ResponseDTO.ok();
        return ResponseEntity
            .status(response.getCode())
            .body(response);
    }

    @PostMapping("/check-password")
    public ResponseEntity<ResponseDTO<Void>> checkPassword(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @Valid @RequestBody PasswordRequestDto passwordRequestDto) {

        memberService.checkPassword(principalDetails, passwordRequestDto);
        ResponseDTO<Void> response = ResponseDTO.ok();
        return ResponseEntity
            .status(response.getCode())
            .body(response);
    }

    @PatchMapping("/profile-image")
    public ResponseEntity<ResponseDTO<ProfileImageResponseDto>> updateProfileImage(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @Valid @RequestBody ProfileImageRequestDto requestDto) {

        ProfileImageResponseDto responseDto = memberService.updateProfileImage(principalDetails, requestDto);
        ResponseDTO<ProfileImageResponseDto> response = ResponseDTO.okWithData(responseDto);

        return ResponseEntity
            .status(response.getCode())
            .body(response);
    }

    @PatchMapping("/introduction")
    public ResponseEntity<ResponseDTO<IntroductionResponseDto>> updateIntroduction(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @Valid @RequestBody IntroductionRequestDto requestDto) {

        IntroductionResponseDto responseDto = memberService.updateIntroduction(principalDetails, requestDto);
        ResponseDTO<IntroductionResponseDto> response = ResponseDTO.okWithData(responseDto);

        return ResponseEntity
            .status(response.getCode())
            .body(response);
    }

    @PatchMapping("/nickname")
    public ResponseEntity<ResponseDTO<NicknameResponseDto>> updateNickname(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @Valid @RequestBody NicknameRequestDto requestDto) {

        NicknameResponseDto responseDto = memberService.updateNickname(principalDetails, requestDto);
        ResponseDTO<NicknameResponseDto> response = ResponseDTO.okWithData(responseDto);

        return ResponseEntity
            .status(response.getCode())
            .body(response);
    }

    @DeleteMapping
    public ResponseEntity<ResponseDTO<Void>> deleteAccount(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        memberService.deleteAccount(principalDetails);
        return ResponseEntity.ok(ResponseDTO.ok());
    }

    @GetMapping("/details")
    public ResponseEntity<ResponseDTO<MemberDetailResponseDto>> getMemberDetails(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        MemberDetailResponseDto responseDto = memberService.getMemberDetails(principalDetails);
        ResponseDTO<MemberDetailResponseDto> response = ResponseDTO.okWithData(responseDto);
        return ResponseEntity
            .status(response.getCode())
            .body(response);
    }
}