package com.haejwo.tripcometrue.domain.comment.triprecord.controller;

import com.haejwo.tripcometrue.domain.comment.triprecord.dto.request.CommentRequestDto;
import com.haejwo.tripcometrue.domain.comment.triprecord.dto.response.TripRecordCommentListResponseDto;
import com.haejwo.tripcometrue.domain.comment.triprecord.service.TripRecordCommentService;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class CommentController {

    private final TripRecordCommentService commentService;

    @PostMapping("/trip-record/{tripRecordId}/comments")
    public ResponseEntity<ResponseDTO<Void>> registerTripRecordComment(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable Long tripRecordId,
            @RequestBody @Valid CommentRequestDto requestDto
    ) {

        commentService.saveComment(principalDetails, tripRecordId, requestDto);
        return ResponseEntity.ok(ResponseDTO.ok());
    }

    @GetMapping("/trip-records/{tripRecordId}/comments")
    public ResponseEntity<ResponseDTO<TripRecordCommentListResponseDto>> getTripRecordCommentList(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable Long tripRecordId,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable
    ) {

        TripRecordCommentListResponseDto responseDto =
                commentService.getTripRecordCommentList(principalDetails, tripRecordId, pageable);
        return ResponseEntity.ok(ResponseDTO.okWithData(responseDto));
    }
}
