package com.haejwo.tripcometrue.domain.comment.placereview.service;

import com.haejwo.tripcometrue.domain.comment.placereview.dto.request.PlaceReviewCommentRequestDto;
import com.haejwo.tripcometrue.domain.comment.placereview.entity.PlaceReviewComment;
import com.haejwo.tripcometrue.domain.comment.placereview.exception.PlaceReviewCommentNotFoundException;
import com.haejwo.tripcometrue.domain.comment.placereview.repository.PlaceReviewCommentRepository;
import com.haejwo.tripcometrue.domain.comment.triprecord.entity.TripRecordComment;
import com.haejwo.tripcometrue.domain.comment.triprecord.exception.TripRecordCommentNotFoundException;
import com.haejwo.tripcometrue.domain.member.entity.Member;
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

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PlaceReviewCommentService {

    private final MemberRepository memberRepository;
    private final PlaceReviewRepository placeReviewRepository;
    private final PlaceReviewCommentRepository placeReviewCommentRepository;

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

    private PlaceReviewComment getPlaceReviewCommentById(Long tripRecordCommentId) {
        return placeReviewCommentRepository.findById(tripRecordCommentId)
                .orElseThrow(PlaceReviewCommentNotFoundException::new);
    }
//
//    @Transactional(readOnly = true)
//    public TripRecordCommentListResponseDto getCommentList(
//            PrincipalDetails principalDetails,
//            Long tripRecordId,
//            Pageable pageable
//    ) {
//
//        Member loginMember = getMember(principalDetails);
//        TripRecord tripRecord = getPlaceReviewById(tripRecordId);
//
//        Slice<TripRecordComment> tripRecordComments = tripRecordCommentRepository.findByTripRecord(tripRecord, pageable);
//        return TripRecordCommentListResponseDto.fromData(tripRecord.getCommentCount(), tripRecordComments, loginMember);
//    }
//
//    public void removeComment(PrincipalDetails principalDetails, Long tripRecordCommentId) {
//
//        Member loginMember = getMember(principalDetails);
//        TripRecordComment tripRecordComment = getTripRecordComment(tripRecordCommentId);
//        TripRecord tripRecord = tripRecordComment.getTripRecord();
//
//        validateRightMemberAccess(loginMember, tripRecordComment);
//
//        int removedCount = getRemovedCount(tripRecordCommentId, tripRecordComment);
//        tripRecord.decreaseCommentCount(removedCount);
//    }
//
//    private int getRemovedCount(Long tripRecordCommentId, TripRecordComment tripRecordComment) {
//        int childrenCount = tripRecordCommentRepository.deleteChildrenByTripRecordCommentId(tripRecordComment.getId());
//        int parentCount = tripRecordCommentRepository.deleteParentByTripRecordCommentId(tripRecordCommentId);
//        return childrenCount + parentCount;
//    }
//
//    private TripRecordComment getTripRecordComment(Long tripRecordCommentId) {
//        return tripRecordCommentRepository.findById(tripRecordCommentId)
//                .orElseThrow(TripRecordCommentNotFoundException::new);
//    }
//
//    private void validateRightMemberAccess(Member member, TripRecordComment tripRecordComment) {
//        if (!Objects.equals(tripRecordComment.getMember().getId(), member.getId())) {
//            throw new UserInvalidAccessException();
//        }
//    }
}
