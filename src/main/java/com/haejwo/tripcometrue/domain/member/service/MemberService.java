package com.haejwo.tripcometrue.domain.member.service;

import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.member.exception.EmailDuplicateException;
import com.haejwo.tripcometrue.domain.member.repository.MemberRepository;
import com.haejwo.tripcometrue.domain.member.request.SignUpRequest;
import com.haejwo.tripcometrue.domain.member.response.SignUpResponse;
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

    public SignUpResponse signup(SignUpRequest signUpRequest) {

        memberRepository.findByMemberBaseEmail(signUpRequest.email()).ifPresent(user -> {
            throw new EmailDuplicateException();
        });

        String encodedPassword = passwordEncoder.encode(signUpRequest.password());

        Member newMember = signUpRequest.toEntity(encodedPassword);
        memberRepository.save(newMember);
        return SignUpResponse.fromEntity(newMember);
    }
}