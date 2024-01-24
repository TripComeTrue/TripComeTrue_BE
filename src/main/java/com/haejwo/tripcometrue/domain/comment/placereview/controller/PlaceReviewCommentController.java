package com.haejwo.tripcometrue.domain.comment.placereview.controller;

import com.haejwo.tripcometrue.domain.comment.placereview.dto.request.PlaceReviewCommentRequestDto;
import com.haejwo.tripcometrue.domain.comment.placereview.service.PlaceReviewCommentService;
import com.haejwo.tripcometrue.domain.comment.triprecord.dto.request.TripRecordCommentRequestDto;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/places/reviews")
public class PlaceReviewCommentController {

    private final PlaceReviewCommentService commentService;

    @PostMapping("/{placeReviewId}/comments")
    public ResponseEntity<ResponseDTO<Void>> registerComment(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable Long placeReviewId,
            @RequestBody @Valid com.haejwo.tripcometrue.domain.comment.placereview.dto.request.PlaceReviewCommentRequestDto requestDto
    ) {

        commentService.saveComment(principalDetails, placeReviewId, requestDto);
        return ResponseEntity.ok(ResponseDTO.ok());
    }

    @PostMapping("/comments/{placeReviewCommentId}/reply-comments")
    public ResponseEntity<ResponseDTO<Void>> registerReplyComment(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable Long placeReviewCommentId,
            @RequestBody @Valid PlaceReviewCommentRequestDto requestDto
    ) {

        commentService.saveReplyComment(principalDetails, placeReviewCommentId, requestDto);
        return ResponseEntity.ok(ResponseDTO.ok());
    }
//
//    @GetMapping("/{tripRecordId}/comments")
//    public ResponseEntity<ResponseDTO<TripRecordCommentListResponseDto>> getCommentList(
//            @AuthenticationPrincipal PrincipalDetails principalDetails,
//            @PathVariable Long tripRecordId,
//            @PageableDefault(sort = "createdAt", direction = Direction.DESC) Pageable pageable
//    ) {
//
//        TripRecordCommentListResponseDto responseDto =
//                commentService.getCommentList(principalDetails, tripRecordId, pageable);
//        return ResponseEntity.ok(ResponseDTO.okWithData(responseDto));
//    }
//
//    @DeleteMapping("/comments/{tripRecordCommentId}")
//    public ResponseEntity<ResponseDTO<Void>> deleteComment(
//            @AuthenticationPrincipal PrincipalDetails principalDetails,
//            @PathVariable Long tripRecordCommentId
//    ) {
//
//        commentService.removeComment(principalDetails, tripRecordCommentId);
//        return ResponseEntity.ok(ResponseDTO.ok());
//    }
}
