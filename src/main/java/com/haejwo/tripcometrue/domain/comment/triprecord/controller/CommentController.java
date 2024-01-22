package com.haejwo.tripcometrue.domain.comment.triprecord.controller;

import com.haejwo.tripcometrue.domain.comment.triprecord.dto.request.CommentRequestDto;
import com.haejwo.tripcometrue.domain.comment.triprecord.service.CommentService;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/trip-record/{tripRecordId}/comments")
    public ResponseEntity<ResponseDTO<Void>> registerTripRecordComment(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable Long tripRecordId,
            @RequestBody @Valid CommentRequestDto requestDto
    ) {

        commentService.saveComment(principalDetails, tripRecordId, requestDto);
        return ResponseEntity.ok(ResponseDTO.ok());
    }
}
