package com.haejwo.tripcometrue.domain.comment.placereview.service;

import com.haejwo.tripcometrue.domain.alarm.entity.AlarmType;
import com.haejwo.tripcometrue.domain.alarm.service.AlarmService;
import com.haejwo.tripcometrue.domain.comment.placereview.dto.request.PlaceReviewCommentRequestDto;
import com.haejwo.tripcometrue.domain.comment.placereview.entity.PlaceReviewComment;
import com.haejwo.tripcometrue.domain.comment.placereview.exception.PlaceReviewCommentNotFoundException;
import com.haejwo.tripcometrue.domain.comment.placereview.repository.PlaceReviewCommentRepository;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.member.exception.UserInvalidAccessException;
import com.haejwo.tripcometrue.domain.member.exception.UserNotFoundException;
import com.haejwo.tripcometrue.domain.member.repository.MemberRepository;
import com.haejwo.tripcometrue.domain.review.placereview.entity.PlaceReview;
import com.haejwo.tripcometrue.domain.review.placereview.exception.PlaceReviewNotFoundException;
import com.haejwo.tripcometrue.domain.review.placereview.repository.PlaceReviewRepository;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PlaceReviewCommentService {

    private final MemberRepository memberRepository;
    private final PlaceReviewRepository placeReviewRepository;
    private final PlaceReviewCommentRepository placeReviewCommentRepository;
    private final AlarmService alarmService;

    public void saveComment(
            PrincipalDetails principalDetails,
            Long placeReviewId,
            com.haejwo.tripcometrue.domain.comment.placereview.dto.request.PlaceReviewCommentRequestDto requestDto
    ) {

        Member loginMember = getMember(principalDetails);
        PlaceReview placeReview = getPlaceReviewById(placeReviewId);

        PlaceReviewComment comment = com.haejwo.tripcometrue.domain.comment.placereview.dto.request.PlaceReviewCommentRequestDto.toComment(loginMember, placeReview, requestDto);
        placeReviewCommentRepository.save(comment);
        placeReview.increaseCommentCount();

        alarmService.addAlarm(
            loginMember,
            placeReview.getMember(),
            AlarmType.NEW_PLACE_REVIEW_COMMENT,
            placeReviewId,
            comment.getId());
    }

    private Member getMember(PrincipalDetails principalDetails) {
        return memberRepository.findById(principalDetails.getMember().getId())
                .orElseThrow(UserNotFoundException::new);
    }

    private PlaceReview getPlaceReviewById(Long placeReviewId) {
        return placeReviewRepository.findById(placeReviewId)
                .orElseThrow(PlaceReviewNotFoundException::new);
    }

    public void saveReplyComment(
            PrincipalDetails principalDetails,
            Long placeReviewCommentId,
            PlaceReviewCommentRequestDto requestDto
    ) {

        Member loginMember = getMember(principalDetails);
        PlaceReviewComment placeReviewComment = getPlaceReviewCommentById(placeReviewCommentId);
        PlaceReview placeReview = placeReviewComment.getPlaceReview();

        PlaceReviewComment comment = PlaceReviewCommentRequestDto.toReplyComment(loginMember, placeReview, placeReviewComment, requestDto);
        placeReviewCommentRepository.save(comment);
        placeReview.increaseCommentCount();
    }

    private PlaceReviewComment getPlaceReviewCommentById(Long placeReviewCommentId) {
        return placeReviewCommentRepository.findById(placeReviewCommentId)
                .orElseThrow(PlaceReviewCommentNotFoundException::new);
    }

    public void removeComment(PrincipalDetails principalDetails, Long placeReviewCommentId) {

        Member loginMember = getMember(principalDetails);
        PlaceReviewComment placeReviewComment = getPlaceReviewComment(placeReviewCommentId);
        PlaceReview placeReview = placeReviewComment.getPlaceReview();

        validateRightMemberAccess(loginMember, placeReviewComment);

        int removedCount = getRemovedCount(placeReviewCommentId, placeReviewComment);
        placeReview.decreaseCommentCount(removedCount);
    }

    private PlaceReviewComment getPlaceReviewComment(Long placeReviewCommentId) {
        return placeReviewCommentRepository.findById(placeReviewCommentId)
                .orElseThrow(PlaceReviewCommentNotFoundException::new);
    }

    private int getRemovedCount(Long placeReviewCommentId, PlaceReviewComment placeReviewComment) {
        int childrenCount = placeReviewCommentRepository.deleteChildrenByPlaceReviewCommentId(placeReviewComment.getId());
        int parentCount = placeReviewCommentRepository.deleteParentByPlaceReviewCommentId(placeReviewCommentId);
        return childrenCount + parentCount;
    }

    private void validateRightMemberAccess(Member member, PlaceReviewComment placeReviewComment) {
        if (!Objects.equals(placeReviewComment.getMember().getId(), member.getId())) {
            throw new UserInvalidAccessException();
        }
    }
}
