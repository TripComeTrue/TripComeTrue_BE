package com.haejwo.tripcometrue.domain.member.service;

import com.haejwo.tripcometrue.domain.member.dto.request.SignUpRequestDto;
import com.haejwo.tripcometrue.domain.member.dto.response.SignUpResponseDto;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.member.exception.EmailDuplicateException;
import com.haejwo.tripcometrue.domain.member.repository.MemberRepository;
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

        Member newMember = signUpRequestDto.toEntity(encodedPassword);
        memberRepository.save(newMember);
        return SignUpResponseDto.fromEntity(newMember);
    }

    public void checkDuplicateEmail(String email) {
        memberRepository.findByMemberBaseEmail(email).ifPresent(user -> {
            throw new EmailDuplicateException();
        });
    }
}