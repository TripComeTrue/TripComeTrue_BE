package com.haejwo.tripcometrue.domain.triprecord.controller;

import com.haejwo.tripcometrue.domain.triprecord.dto.request.ModelAttribute.TripRecordListRequestAttribute;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord.MyTripRecordListResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord.TopTripRecordResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord.TripRecordDetailResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord.TripRecordListResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord_schedule_media.TripRecordHotShortsListResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord_schedule_media.TripRecordScheduleVideoDetailDto;
import com.haejwo.tripcometrue.domain.triprecord.service.TripRecordService;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import com.haejwo.tripcometrue.global.validator.annotation.HomeTopListQueryType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

        return  ResponseEntity
                    .status(responseBody.getCode())
                    .body(responseBody);

    }

    // 홈 피드 TOP 인기 여행 후기 리스트 조회
    @GetMapping("/top-list")
    public ResponseEntity<ResponseDTO<List<TopTripRecordResponseDto>>> tripRecordTopList(
        @RequestParam("type") @HomeTopListQueryType String type
    ) {
        return ResponseEntity
            .ok()
            .body(
                ResponseDTO.okWithData(
                    tripRecordService.findTopTripRecordList(type)
                )
            );
    }

    @GetMapping("/my")
    public ResponseEntity<ResponseDTO<Page<MyTripRecordListResponseDto>>> getMyTripRecords(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        Pageable pageable
    ) {
        Page<MyTripRecordListResponseDto> responseDtos
            = tripRecordService.getMyTripRecordsList(principalDetails, pageable);
        ResponseDTO<Page<MyTripRecordListResponseDto>> responseBody
            = ResponseDTO.okWithData(responseDtos);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(responseBody);

    }

    @GetMapping("hot-shorts-list")
    public ResponseEntity<ResponseDTO<List<TripRecordHotShortsListResponseDto>>> tripRecordHotShortsList(
        Pageable pageable
    ) {
        List<TripRecordHotShortsListResponseDto> responseDtos = tripRecordService.findTripRecordHotShortsList(pageable);

        ResponseDTO<List<TripRecordHotShortsListResponseDto>> responseBody = ResponseDTO.okWithData(responseDtos);

        return ResponseEntity
            .status(responseBody.getCode())
            .body(responseBody);
    }

    @GetMapping("shorts/{videoId}")
    public ResponseEntity<ResponseDTO<TripRecordScheduleVideoDetailDto>> tripRecordShortsDetail(
        @PathVariable Long videoId
    ) {

        TripRecordScheduleVideoDetailDto responseDto = tripRecordService.findTripRecordShortsDetail(videoId);

        ResponseDTO<TripRecordScheduleVideoDetailDto> responseBody = ResponseDTO.okWithData(responseDto);

        return ResponseEntity
            .status(responseBody.getCode())
            .body(responseBody);
    }

}
