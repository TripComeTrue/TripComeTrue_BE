package com.haejwo.tripcometrue.domain.triprecord.controller;

import com.haejwo.tripcometrue.domain.triprecord.dto.request.ModelAttribute.TripRecordListRequestAttribute;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord.MyTripRecordListResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.request.ModelAttribute.TripRecordSearchParamAttribute;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord.TripRecordListItemResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord.TripRecordDetailResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord.TripRecordListResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord_schedule_media.TripRecordHotShortsListResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord_schedule_media.TripRecordScheduleVideoDetailDto;
import com.haejwo.tripcometrue.domain.triprecord.entity.type.ExpenseRangeType;
import com.haejwo.tripcometrue.domain.triprecord.service.TripRecordService;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import com.haejwo.tripcometrue.global.util.SliceResponseDto;
import com.haejwo.tripcometrue.global.validator.annotation.HomeTopListQueryType;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    // 여행 후기 검색(총 일수, 도시명, 여행지명, 도시 식별자) 및 페이징 조회
    @GetMapping("/search")
    public ResponseEntity<ResponseDTO<SliceResponseDto<TripRecordListItemResponseDto>>> searchTripRecords(
        @ModelAttribute TripRecordSearchParamAttribute searchParamAttribute,
        @PageableDefault(sort = "averageRating", direction = Sort.Direction.DESC) Pageable pageable
    ) {

        return ResponseEntity
            .ok()
            .body(
                ResponseDTO.okWithData(
                    tripRecordService.findTripRecordList(searchParamAttribute, pageable)
                )
            );
    }

    // 여행 후기 검색(해시태그) 및 페이징 조회
    @GetMapping("/search/hashtags")
    public ResponseEntity<ResponseDTO<SliceResponseDto<TripRecordListItemResponseDto>>> searchTripRecordsByHashtag(
        @RequestParam("hashtag") String hashtag,
        @PageableDefault(sort = "averageRating", direction = Sort.Direction.DESC) Pageable pageable
    ) {

        return ResponseEntity
            .ok()
            .body(
                ResponseDTO.okWithData(
                    tripRecordService.listTripRecordsByHashtag(hashtag, pageable)
                )
            );
    }

    // 여행 후기 검색(여행 경비 범위) 및 페이징 조회
    @GetMapping("/search/expense-ranges")
    public ResponseEntity<ResponseDTO<SliceResponseDto<TripRecordListItemResponseDto>>> searchTripRecordsByExpenseRangeType(
        @RequestParam("expenseRangeType")
        @NotNull(message = "요청값은 [BELOW_50, BELOW_100, BELOW_200, BELOW_300, ABOVE_300] 중 하나여야 합니다.")
        ExpenseRangeType expenseRangeType,
        @PageableDefault(sort = "averageRating", direction = Sort.Direction.DESC) Pageable pageable
    ) {

        return ResponseEntity
            .ok()
            .body(
                ResponseDTO.okWithData(
                    tripRecordService.listTripRecordsByExpenseRangeType(expenseRangeType, pageable)
                )
            );
    }

    // 홈 피드 TOP 인기 여행 후기 리스트 조회
    @GetMapping("/top-list")
    public ResponseEntity<ResponseDTO<List<TripRecordListItemResponseDto>>> tripRecordTopList(
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
