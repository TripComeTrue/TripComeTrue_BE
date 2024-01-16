package com.haejwo.tripcometrue.domain.triprecord.controller;

import com.haejwo.tripcometrue.domain.triprecord.dto.response.ModelAttribute.TripRecordListRequestAttribute;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord.TripRecordListResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord.TripRecordDetailResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.service.TripRecordService;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

        TripRecordDetailResponseDto responseDto = tripRecordService.findTripRecord(principalDetails,
            tripRecordId);
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

        ResponseDTO<List<TripRecordListResponseDto>> responseBody = ResponseDTO.okWithData(
            responseDtos);
        ResponseDTO<List<TripRecordListResponseDto>> responseBody = ResponseDTO.okWithData(responseDtos);

        return  ResponseEntity
                    .status(responseBody.getCode())
                    .body(responseBody);

    }

    @GetMapping("/my-trip-records-list")
    public ResponseEntity<ResponseDTO<List<TripRecordListResponseDto>>> getMyTripRecords(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        Pageable pageable
    ) {
        List<TripRecordListResponseDto> responseDtos
            = tripRecordService.findMyTripRecordsList(principalDetails, pageable);
        ResponseDTO<List<TripRecordListResponseDto>> responseBody
            = ResponseDTO.okWithData(responseDtos);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(responseBody);
    }
}
