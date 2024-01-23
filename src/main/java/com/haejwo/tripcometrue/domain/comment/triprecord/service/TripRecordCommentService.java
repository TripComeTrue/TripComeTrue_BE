package com.haejwo.tripcometrue.domain.comment.triprecord.service;

import com.haejwo.tripcometrue.domain.comment.triprecord.dto.request.CommentRequestDto;
import com.haejwo.tripcometrue.domain.comment.triprecord.dto.response.TripRecordCommentListResponseDto;
import com.haejwo.tripcometrue.domain.comment.triprecord.entity.TripRecordComment;
import com.haejwo.tripcometrue.domain.comment.triprecord.exception.TripRecordCommentNotFoundException;
import com.haejwo.tripcometrue.domain.comment.triprecord.repository.TripRecordCommentRepository;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.member.exception.UserInvalidAccessException;
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

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class TripRecordCommentService {

    private final MemberRepository memberRepository;
    private final TripRecordRepository tripRecordRepository;
    private final TripRecordCommentRepository tripRecordCommentRepository;

    public void saveComment(
            PrincipalDetails principalDetails,
            Long tripRecordId,
            CommentRequestDto requestDto
    ) {

        Member loginMember = getMember(principalDetails);
        TripRecord tripRecord = getTripRecordById(tripRecordId);

        TripRecordComment comment = CommentRequestDto.toComment(loginMember, tripRecord, requestDto);
        tripRecordCommentRepository.save(comment);
    }

    private Member getMember(PrincipalDetails principalDetails) {
        return memberRepository.findById(principalDetails.getMember().getId())
                .orElseThrow(UserNotFoundException::new);
    }

    public void saveReplyComment(
            PrincipalDetails principalDetails,
            Long tripRecordId,
            Long tripRecordCommentId,
            CommentRequestDto requestDto
    ) {

        Member loginMember = getMember(principalDetails);
        TripRecord tripRecord = getTripRecordById(tripRecordId);
        TripRecordComment tripRecordComment = getTripRecordCommentById(tripRecordCommentId);

        TripRecordComment comment = CommentRequestDto.toReplyComment(loginMember, tripRecord, tripRecordComment, requestDto);
        tripRecordCommentRepository.save(comment);
    }

    private TripRecordComment getTripRecordCommentById(Long tripRecordCommentId) {
        return tripRecordCommentRepository.findById(tripRecordCommentId)
                .orElseThrow(TripRecordCommentNotFoundException::new);
    }

    private TripRecord getTripRecordById(Long tripRecordId) {
        return tripRecordRepository.findById(tripRecordId)
                .orElseThrow(TripRecordNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public TripRecordCommentListResponseDto getCommentList(
            PrincipalDetails principalDetails,
            Long tripRecordId,
            Pageable pageable
    ) {

        Member loginMember = getMember(principalDetails);
        TripRecord tripRecord = getTripRecordById(tripRecordId);

        Page<TripRecordComment> tripRecordComments = tripRecordCommentRepository.findByTripRecord(tripRecord, pageable);
        return TripRecordCommentListResponseDto.fromData(tripRecordComments, loginMember);
    }

    public void removeComment(PrincipalDetails principalDetails, Long tripRecordCommentId) {

        Member loginMember = getMember(principalDetails);
        TripRecordComment tripRecordComment = getTripRecordComment(tripRecordCommentId);
        validateRightMemberAccess(loginMember, tripRecordComment);

        tripRecordCommentRepository.delete(tripRecordComment);
    }

    private TripRecordComment getTripRecordComment(Long tripRecordCommentId) {
        return tripRecordCommentRepository.findById(tripRecordCommentId)
                .orElseThrow(TripRecordCommentNotFoundException::new);
    }

    private void validateRightMemberAccess(Member member, TripRecordComment tripRecordComment) {
        if (!Objects.equals(tripRecordComment.getMember().getId(), member.getId())) {
            throw new UserInvalidAccessException();
        }
    }
}
