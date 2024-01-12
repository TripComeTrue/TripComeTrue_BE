package com.haejwo.tripcometrue.domain.triprecord.controller;

import com.haejwo.tripcometrue.domain.triprecord.dto.request.CreateSchedulePlaceRequestDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.request.TripRecordRequestDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord_schedule.SearchScheduleTripResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.service.TripRecordEditService;
import com.haejwo.tripcometrue.global.enums.Country;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TripRecordEditController {

    private final TripRecordEditService tripRecordEditService;

    @PostMapping("/v1/trip-record")
    public ResponseEntity<ResponseDTO<Void>> tripRecordAdd(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestBody TripRecordRequestDto requestDto
    ) {

        tripRecordEditService.addTripRecord(principalDetails, requestDto);
        ResponseDTO<Void> responseBody = ResponseDTO.ok();

        return ResponseEntity
            .status(responseBody.getCode())
            .body(responseBody);
    }

    @GetMapping("/v1/search-schedule-places")
    public ResponseEntity<ResponseDTO<List<SearchScheduleTripResponseDto>>> searchSchedulePlace(
        @RequestParam Country country,
        @RequestParam String city
    ) {
        ResponseDTO<List<SearchScheduleTripResponseDto>> responseBody
            = ResponseDTO.okWithData(tripRecordEditService.searchSchedulePlace(country, city));

        return ResponseEntity
            .status(responseBody.getCode())
            .body(responseBody);
    }

    @PostMapping("/v1/schedule-place")
    public ResponseEntity<ResponseDTO<Long>> createSchedulePlace(
        @RequestBody CreateSchedulePlaceRequestDto createSchedulePlaceRequestDto
    ) {
        ResponseDTO<Long> responseBody
            = ResponseDTO.okWithData(
            tripRecordEditService.createSchedulePlace(createSchedulePlaceRequestDto));

        return ResponseEntity
            .status(responseBody.getCode())
            .body(responseBody);
    }
}
