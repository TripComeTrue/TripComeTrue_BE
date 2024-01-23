package com.haejwo.tripcometrue.domain.comment.triprecord.service;

import com.haejwo.tripcometrue.domain.comment.triprecord.dto.request.CommentRequestDto;
import com.haejwo.tripcometrue.domain.comment.triprecord.dto.response.TripRecordCommentListResponseDto;
import com.haejwo.tripcometrue.domain.comment.triprecord.entity.TripRecordComment;
import com.haejwo.tripcometrue.domain.comment.triprecord.repository.TripRecordCommentRepository;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.member.exception.UserNotFoundException;
import com.haejwo.tripcometrue.domain.member.repository.MemberRepository;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import com.haejwo.tripcometrue.domain.triprecord.exception.TripRecordNotFoundException;
import com.haejwo.tripcometrue.domain.triprecord.repository.triprecord.TripRecordRepository;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TripRecordCommentService {

    private final MemberRepository memberRepository;
    private final TripRecordRepository tripRecordRepository;
    private final TripRecordCommentRepository tripRecordCommentRepository;

    @Transactional
    public void saveComment(
            PrincipalDetails principalDetails,
            Long tripRecordId,
            CommentRequestDto requestDto
    ) {

        Member loginMember = getMember(principalDetails);
        TripRecord tripRecord = getTripRecordById(tripRecordId);

        TripRecordComment comment = CommentRequestDto.toEntity(loginMember, tripRecord, requestDto);
        tripRecordCommentRepository.save(comment);
    }

    private Member getMember(PrincipalDetails principalDetails) {
        return memberRepository.findById(principalDetails.getMember().getId())
                .orElseThrow(UserNotFoundException::new);
    }

    private TripRecord getTripRecordById(Long tripRecordId) {
        return tripRecordRepository.findById(tripRecordId)
                .orElseThrow(TripRecordNotFoundException::new);
    }

    public TripRecordCommentListResponseDto getTripRecordCommentList(
            PrincipalDetails principalDetails,
            Long tripRecordId,
            Pageable pageable
    ) {

        Member loginMember = getMember(principalDetails);
        TripRecord tripRecord = getTripRecordById(tripRecordId);

        Page<TripRecordComment> tripRecordComments = tripRecordCommentRepository.findByTripRecord(tripRecord, pageable);
        return TripRecordCommentListResponseDto.fromData(tripRecordComments, loginMember);
    }
}
