package com.haejwo.tripcometrue.domain.comment.triprecord.controller;

import com.haejwo.tripcometrue.domain.comment.triprecord.dto.request.CommentRequestDto;
import com.haejwo.tripcometrue.domain.comment.triprecord.dto.response.TripRecordCommentListResponseDto;
import com.haejwo.tripcometrue.domain.comment.triprecord.service.TripRecordCommentService;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.domain.Sort.Direction;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/trip-records")
public class TripRecordCommentController {

    private final TripRecordCommentService commentService;

    @PostMapping("/{tripRecordId}/comments")
    public ResponseEntity<ResponseDTO<Void>> registerComment(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable Long tripRecordId,
            @RequestBody @Valid CommentRequestDto requestDto
    ) {

        commentService.saveComment(principalDetails, tripRecordId, requestDto);
        return ResponseEntity.ok(ResponseDTO.ok());
    }

    @PostMapping("/{tripRecordId}/comments/{tripRecordCommentId}")
    public ResponseEntity<ResponseDTO<Void>> registerReplyComment(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable Long tripRecordId,
            @PathVariable Long tripRecordCommentId,
            @RequestBody @Valid CommentRequestDto requestDto
    ) {

        commentService.saveReplyComment(principalDetails, tripRecordId, tripRecordCommentId, requestDto);
        return ResponseEntity.ok(ResponseDTO.ok());
    }

    @GetMapping("/{tripRecordId}/comments")
    public ResponseEntity<ResponseDTO<TripRecordCommentListResponseDto>> getCommentList(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable Long tripRecordId,
            @PageableDefault(sort = "createdAt", direction = Direction.DESC) Pageable pageable
    ) {

        TripRecordCommentListResponseDto responseDto =
                commentService.getCommentList(principalDetails, tripRecordId, pageable);
        return ResponseEntity.ok(ResponseDTO.okWithData(responseDto));
    }

    @DeleteMapping("/comments/{tripRecordCommentId}")
    public ResponseEntity<ResponseDTO<Void>> deleteComment(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable Long tripRecordCommentId
    ) {

        commentService.removeComment(principalDetails, tripRecordCommentId);
        return ResponseEntity.ok(ResponseDTO.ok());
    }
}
