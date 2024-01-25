package com.haejwo.tripcometrue.domain.member.service;

import com.haejwo.tripcometrue.domain.member.dto.response.MemberSimpleResponseDto;
import com.haejwo.tripcometrue.domain.member.exception.UserNotFoundException;
import com.haejwo.tripcometrue.domain.member.repository.MemberRepository;
import com.haejwo.tripcometrue.domain.triprecordViewHistory.repository.TripRecordViewHistoryRepository;
import com.haejwo.tripcometrue.global.util.SliceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberReadSearchService {

    private final MemberRepository memberRepository;
    private final TripRecordViewHistoryRepository tripRecordViewHistoryRepository;
    private static final int HOME_TOP_CREATORS_SIZE = 10;

    @Transactional(readOnly = true)
    public MemberSimpleResponseDto getMemberSimpleInfo(Long memberId) {
        return MemberSimpleResponseDto.fromEntity(
            memberRepository.findById(memberId)
                .orElseThrow(UserNotFoundException::new)
        );
    }

    @Transactional(readOnly = true)
    public List<MemberSimpleResponseDto> listTopMemberSimpleInfos() {
        LocalDate current = LocalDate.now();
        LocalDateTime start = current.minusDays(3).atStartOfDay();
        LocalDateTime end = current.minusDays(1).atTime(23, 59, 59);

        return tripRecordViewHistoryRepository
            .findTopListMembers(start, end, HOME_TOP_CREATORS_SIZE)
            .stream()
            .map(dto -> MemberSimpleResponseDto.builder()
                .memberId(dto.memberId())
                .nickname(dto.nickname())
                .introduction(dto.introduction())
                .profileImageUrl(dto.profileImageUrl())
                .build()
            ).toList();
    }

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
