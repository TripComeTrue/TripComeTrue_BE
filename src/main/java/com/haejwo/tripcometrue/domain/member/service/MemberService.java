package com.haejwo.tripcometrue.domain.member.service;

import com.haejwo.tripcometrue.domain.member.dto.request.IntroductionRequestDto;
import com.haejwo.tripcometrue.domain.member.dto.request.NicknameRequestDto;
import com.haejwo.tripcometrue.domain.member.dto.request.PasswordRequestDto;
import com.haejwo.tripcometrue.domain.member.dto.request.ProfileImageRequestDto;
import com.haejwo.tripcometrue.domain.member.dto.request.SignUpRequestDto;
import com.haejwo.tripcometrue.domain.member.dto.response.*;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.member.exception.*;
import com.haejwo.tripcometrue.domain.member.repository.MemberRepository;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
            "용기있는", "심심한", "잘생긴", "이쁜", "눈웃음치는", "행복한", "사랑스러운", "순수한");
        List<String> name = Arrays.asList("사자", "코끼리", "호랑이", "곰", "여우", "늑대", "너구리",
            "참새", "고슴도치", "강아지", "고양이", "거북이", "토끼", "앵무새", "하이에나", "펭귄", "하마",
            "얼룩말", "치타", "악어", "기린", "수달", "염소", "다람쥐", "판다", "코알라", "앵무새", "독수리", "알파카");
        Collections.shuffle(first);
        Collections.shuffle(name);
        return first.get(0) + name.get(0);
    }

    public void changePassword(
        PrincipalDetails principalDetails, PasswordRequestDto passwordRequestDto) {
        Member member = getLoginMember(principalDetails);

        String currentPassword = member.getMemberBase().getPassword();
        String newPassword = passwordRequestDto.newPassword();

        //새 비밀번호, 현재 비밀번호와 동일여부  최종 검증
        if (passwordEncoder.matches(newPassword, currentPassword)) {
            throw new NewPasswordSameAsOldException();
        }

        //새비밀번호 재입력값 동일여부 최종 검증
        if (!passwordRequestDto.newPassword().equals(passwordRequestDto.confirmPassword())) {
            throw new NewPasswordNotMatchException();
        }

        String encodedNewPassword = passwordEncoder.encode(newPassword);
        member.getMemberBase().changePassword(encodedNewPassword);
        memberRepository.save(member);
    }

    public void checkPassword(
        PrincipalDetails principalDetails, PasswordRequestDto passwordRequestDto) {
        Member member = principalDetails.getMember();
        String currentPassword = member.getMemberBase().getPassword();
        String inPuttedCurrentPassword = passwordRequestDto.currentPassword();
        String newPassword = passwordRequestDto.newPassword();

        // 현재 비밀번호만 입력된 경우
        if (passwordRequestDto.currentPassword() != null
            && passwordRequestDto.newPassword() == null) {
            if (!passwordEncoder.matches(passwordRequestDto.currentPassword(), currentPassword)) {
                throw new CurrentPasswordNotMatchException();
            }
        }

        // 새 비밀번호만 입력된 경우
        if (passwordRequestDto.newPassword() != null
            && passwordRequestDto.currentPassword() == null) {
            if (passwordEncoder.matches(passwordRequestDto.newPassword(), currentPassword)) {
                throw new NewPasswordSameAsOldException();
            }
        }

        // 새 비밀번호 확인이 입력된 경우
        if (passwordRequestDto.confirmPassword() != null) {
            if (!passwordRequestDto.newPassword().equals(passwordRequestDto.confirmPassword())) {
                throw new NewPasswordNotMatchException();
            }
        }
    }

    public ProfileImageResponseDto updateProfileImage(
        PrincipalDetails principalDetails, ProfileImageRequestDto requestDto) {
        Member member = getLoginMember(principalDetails);

        member.updateProfileImage(requestDto.profile_image());
        memberRepository.save(member);

        return ProfileImageResponseDto.fromEntity(member);
    }

    public IntroductionResponseDto updateIntroduction(
        PrincipalDetails principalDetails, IntroductionRequestDto requestDto) {
        if (requestDto.introduction().length() > 20) {
            throw new IntroductionLengthExceededException();
        }

        Member member = getLoginMember(principalDetails);
        member.updateIntroduction(requestDto.introduction());

        return IntroductionResponseDto.fromEntity(member);
    }

    public NicknameResponseDto updateNickname(
        PrincipalDetails principalDetails, NicknameRequestDto requestDto) {
        Member member = memberRepository.findById(principalDetails.getMember().getId())
            .orElseThrow();

        memberRepository.findByMemberBaseNickname(requestDto.nickname())
            .ifPresent(existingMember -> {
                throw new NicknameAlreadyExistsException();
            });

        if(member.getNickNameChangeTime() != null &&
            ChronoUnit.MONTHS.between(member.getNickNameChangeTime(), LocalDateTime.now()) < 6){
            throw new NicknameChangeNotAvailableException();
        }

        member.getMemberBase().changeNickname(requestDto.nickname());
        member.updateNickNameChangeCount();
        member.updateNickNameChangeTime(LocalDateTime.now());

        return NicknameResponseDto.fromEntity(member);
    }

    public void deleteAccount(PrincipalDetails principalDetails) {

        Member member = memberRepository.findById(principalDetails.getMember().getId())
            .orElseThrow();

        memberRepository.delete(member);
    }

    public Member getLoginMember(PrincipalDetails principalDetails) {
        Member member = principalDetails.getMember();
        return member;
    }

    public MemberDetailResponseDto getMemberDetails(PrincipalDetails principalDetails) {
        Member member = getLoginMember(principalDetails);
        return MemberDetailResponseDto.fromEntity(member);
    }

    public MemberSimpleResponseDto getMemberSimpleInfo(Long memberId) {
        return MemberSimpleResponseDto.fromEntity(
            memberRepository.findById(memberId)
                .orElseThrow(UserNotFoundException::new)
        );
    }
}

