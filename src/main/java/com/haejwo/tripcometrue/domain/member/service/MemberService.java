package com.haejwo.tripcometrue.domain.member.service;

import com.haejwo.tripcometrue.domain.member.dto.request.SignUpRequestDto;
import com.haejwo.tripcometrue.domain.member.dto.response.SignUpResponseDto;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.member.exception.EmailDuplicateException;
import com.haejwo.tripcometrue.domain.member.repository.MemberRepository;
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
}