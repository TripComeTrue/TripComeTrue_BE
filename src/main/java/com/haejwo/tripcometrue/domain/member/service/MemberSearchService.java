package com.haejwo.tripcometrue.domain.member.service;

import com.haejwo.tripcometrue.domain.member.dto.response.MemberSimpleResponseDto;
import com.haejwo.tripcometrue.domain.member.repository.MemberRepository;
import com.haejwo.tripcometrue.global.util.SliceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberSearchService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<MemberSimpleResponseDto> searchByNickname(String nickname) {
        return memberRepository
            .findByNicknameOrderByMemberRating(nickname)
            .stream()
            .map(MemberSimpleResponseDto::fromEntity)
            .toList();
    }

    @Transactional(readOnly = true)
    public SliceResponseDto<MemberSimpleResponseDto> searchByNickname(String nickname, Pageable pageable) {

        return SliceResponseDto.of(
            memberRepository
                .findByNicknameOrderByMemberRating(nickname, pageable)
                .map(MemberSimpleResponseDto::fromEntity)
        );
    }
}
