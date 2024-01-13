package com.haejwo.tripcometrue.domain.member.service;

import com.haejwo.tripcometrue.domain.member.dto.request.EmailCheckRequestDto;
import com.haejwo.tripcometrue.domain.member.dto.request.IntroductionRequestDto;
import com.haejwo.tripcometrue.domain.member.dto.request.NicknameRequestDto;
import com.haejwo.tripcometrue.domain.member.dto.request.PasswordChangeRequestDto;
import com.haejwo.tripcometrue.domain.member.dto.request.PasswordCheckRequestDto;
import com.haejwo.tripcometrue.domain.member.dto.request.ProfileImageRequestDto;
import com.haejwo.tripcometrue.domain.member.dto.request.SignUpRequestDto;
import com.haejwo.tripcometrue.domain.member.dto.response.IntroductionResponseDto;
import com.haejwo.tripcometrue.domain.member.dto.response.NicknameResponseDto;
import com.haejwo.tripcometrue.domain.member.dto.response.ProfileImageResponseDto;
import com.haejwo.tripcometrue.domain.member.dto.response.SignUpResponseDto;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.member.exception.CurrentPasswordNotMatchException;
import com.haejwo.tripcometrue.domain.member.exception.EmailDuplicateException;
import com.haejwo.tripcometrue.domain.member.exception.EmailNotMatchException;
import com.haejwo.tripcometrue.domain.member.exception.NewPasswordSameAsOldException;
import com.haejwo.tripcometrue.domain.member.exception.NicknameAlreadyExistsException;
import com.haejwo.tripcometrue.domain.member.repository.MemberRepository;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public SignUpResponseDto signup(SignUpRequestDto signUpRequestDto) {

        memberRepository.findByMemberBaseEmail(signUpRequestDto.email()).ifPresent(user -> {
            throw new EmailDuplicateException();
        });

        String encodedPassword = passwordEncoder.encode(signUpRequestDto.password());

        Member newMember = signUpRequestDto.toEntity(encodedPassword, generateName());
        memberRepository.save(newMember);
        return SignUpResponseDto.fromEntity(newMember);
    }

    public void checkDuplicateEmail(String email) {
        memberRepository.findByMemberBaseEmail(email).ifPresent(user -> {
            throw new EmailDuplicateException();
        });
    }

    public String generateName() {
        List<String> first = Arrays.asList("자유로운", "서운한",
            "당당한", "배부른", "수줍은", "멋있는",
            "열받은", "심심한", "잘생긴", "이쁜", "시끄러운");
        List<String> name = Arrays.asList("사자", "코끼리", "호랑이", "곰", "여우", "늑대", "너구리",
            "참새", "고슴도치", "강아지", "고양이", "거북이", "토끼", "앵무새", "하이에나", "돼지", "하마",
            "얼룩말", "치타", "악어", "기린", "수달", "염소", "다람쥐", "판다");
        Collections.shuffle(first);
        Collections.shuffle(name);
        return first.get(0) + name.get(0);
    }

    @Transactional
    public void changePassword(
        PrincipalDetails principalDetails, PasswordChangeRequestDto passwordChangeRequestDto) {
        Long memberId = principalDetails.getMember().getId();
        Member member = memberRepository.findById(memberId)
            .orElseThrow();

        String currentPassword = member.getMemberBase().getPassword();
        String newPassword = passwordChangeRequestDto.newPassword();

        if (passwordEncoder.matches(newPassword, currentPassword)) {
            throw new NewPasswordSameAsOldException();
        }

        String encodedNewPassword = passwordEncoder.encode(newPassword);
        member.getMemberBase().changePassword(encodedNewPassword);
        memberRepository.save(member);
    }

    public void checkPassword(
        PrincipalDetails principalDetails, PasswordCheckRequestDto passwordCheckRequestDto) {
        Member member = principalDetails.getMember();
        String currentPassword = member.getMemberBase().getPassword();

        if (!passwordEncoder.matches(passwordCheckRequestDto.currentPassword(), currentPassword)) {
            throw new CurrentPasswordNotMatchException();
        }
    }

    public void checkEmail(
        PrincipalDetails principalDetails, EmailCheckRequestDto emailCheckRequestDto) {
        Member member = principalDetails.getMember();
        String currentEmail = member.getMemberBase().getEmail();

        if (!emailCheckRequestDto.email().equals(currentEmail)) {
            throw new EmailNotMatchException();
        }
    }

    public ProfileImageResponseDto updateProfileImage(
        PrincipalDetails principalDetails, ProfileImageRequestDto requestDto) {
        Member member = memberRepository.findById(principalDetails.getMember().getId())
            .orElseThrow();

        member.updateProfileImage(requestDto.profile_image());
        memberRepository.save(member);

        return ProfileImageResponseDto.fromEntity(member);

    }

    public IntroductionResponseDto updateIntroduction(
        PrincipalDetails principalDetails, IntroductionRequestDto requestDto) {
        Member member = memberRepository.findById(principalDetails.getMember().getId())
            .orElseThrow();

        member.updateIntroduction(requestDto.introduction());
        memberRepository.save(member);

        return IntroductionResponseDto.fromEntity(member);
    }

    public NicknameResponseDto updateNickname(
        PrincipalDetails principalDetails, NicknameRequestDto requestDto) {
        memberRepository.findByMemberBaseNickname(requestDto.nickname())
            .ifPresent(existingMember -> {throw new NicknameAlreadyExistsException();});

        Member member = memberRepository.findById(principalDetails.getMember().getId())
            .orElseThrow();

        member.getMemberBase().changeNickname(requestDto.nickname());
        memberRepository.save(member);

        return NicknameResponseDto.fromEntity(member);
    }

    public void deleteAccount(PrincipalDetails principalDetails) {

        Member member = memberRepository.findById(principalDetails.getMember().getId())
            .orElseThrow();

        memberRepository.delete(member);
    }
}