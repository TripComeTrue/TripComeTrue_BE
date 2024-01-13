package com.haejwo.tripcometrue.domain.triprecord.controller;

import com.haejwo.tripcometrue.domain.triprecord.dto.response.ModelAttribute.TripRecordListRequestAttribute;
import com.haejwo.tripcometrue.domain.triprecord.dto.request.TripRecordRequestDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.TripRecordListResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord.TripRecordDetailResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord.TripRecordResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.service.TripRecordService;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/trip-records")
@RequiredArgsConstructor
public class TripRecordController {

    private final TripRecordService tripRecordService;


    @GetMapping("/{tripRecordId}")
    public ResponseEntity<ResponseDTO<TripRecordDetailResponseDto>> tripRecordDetail(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable Long tripRecordId
    ) {

        TripRecordDetailResponseDto responseDto = tripRecordService.findTripRecord(principalDetails, tripRecordId);
        ResponseDTO<TripRecordDetailResponseDto> responseBody = ResponseDTO.okWithData(responseDto);

        return ResponseEntity
            .status(responseBody.getCode())
            .body(responseBody);
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<TripRecordListResponseDto>>> tripRecordList(
        Pageable pageable,
        @ModelAttribute TripRecordListRequestAttribute request
    ) {

        List<TripRecordListResponseDto> responseDtos
            = tripRecordService.findTripRecordList(pageable, request);

        ResponseDTO<List<TripRecordListResponseDto>> responseBody = ResponseDTO.okWithData(responseDtos);

        return  ResponseEntity
                    .status(responseBody.getCode())
                    .body(responseBody);

    }

    @PutMapping("/{tripRecordId}")
    public ResponseEntity<ResponseDTO<TripRecordResponseDto>> tripRecordModify(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable Long tripRecordId,
        @RequestBody TripRecordRequestDto requestDto
    ) {

        TripRecordResponseDto responseDto = tripRecordService.modifyTripRecord(principalDetails, tripRecordId, requestDto);
        ResponseDTO<TripRecordResponseDto> responseBody = ResponseDTO.okWithData(responseDto);

        return ResponseEntity
            .status(responseBody.getCode())
            .body(responseBody);

    }

    @DeleteMapping("/{tripRecordId}")
    public ResponseEntity<ResponseDTO> tripRecordRemove(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable Long tripRecordId
    ) {

        tripRecordService.removeTripRecord(principalDetails, tripRecordId);
        ResponseDTO responseBody = ResponseDTO.ok();

        return ResponseEntity
            .status(responseBody.getCode())
            .body(responseBody);
    }

}
