package com.haejwo.tripcometrue.domain.tripplan.controller;

import com.haejwo.tripcometrue.domain.tripplan.dto.request.TripPlanRequestDto;
import com.haejwo.tripcometrue.domain.tripplan.dto.response.CopyTripPlanFromTripRecordResponseDto;
import com.haejwo.tripcometrue.domain.tripplan.dto.response.TripPlanDetailsResponseDto;
import com.haejwo.tripcometrue.domain.tripplan.dto.response.TripPlanListReponseDto;
import com.haejwo.tripcometrue.domain.tripplan.sevice.TripPlanService;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/trip-plan")
@RequiredArgsConstructor
public class TripPlanController {

    private final TripPlanService tripPlanService;

    @PostMapping
    public ResponseEntity<ResponseDTO<Void>> createTripPlan(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestBody TripPlanRequestDto requestDto
    ) {

        tripPlanService.addTripPlan(principalDetails, requestDto);
        ResponseDTO<Void> responseBody = ResponseDTO.ok();

        return ResponseEntity
            .status(responseBody.getCode())
            .body(responseBody);
    }

    @DeleteMapping("/{planId}")
    public ResponseEntity<ResponseDTO<Void>> deleteTripPlan(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable Long planId) {

        tripPlanService.deleteTripPlan(principalDetails, planId);
        ResponseDTO<Void> responseBody = ResponseDTO.ok();

        return ResponseEntity
            .status(responseBody.getCode())
            .body(responseBody);
    }

    @PutMapping("/{planId}")
    public ResponseEntity<ResponseDTO<Void>> modifyTripPlan(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestBody TripPlanRequestDto requestDto,
        @PathVariable Long planId) {

        tripPlanService.modifyTripPlan(principalDetails, planId, requestDto);
        ResponseDTO<Void> responseBody = ResponseDTO.ok();

        return ResponseEntity
            .status(responseBody.getCode())
            .body(responseBody);
    }

    @GetMapping("/{planId}")
    public ResponseEntity<ResponseDTO<TripPlanDetailsResponseDto>> getTripPlanDetails(
        @PathVariable Long planId) {

        ResponseDTO<TripPlanDetailsResponseDto> responseBody = ResponseDTO.okWithData(
            tripPlanService.getTripPlanDetails(planId));

        return ResponseEntity
            .status(responseBody.getCode())
            .body(responseBody);
    }

    @PostMapping("from-trip-record/{tripRecordId}")
    public ResponseEntity<ResponseDTO<CopyTripPlanFromTripRecordResponseDto>> copyTripPlanFromTripRecord(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable Long tripRecordId) {

        ResponseDTO<CopyTripPlanFromTripRecordResponseDto> responseBody = ResponseDTO.okWithData(
            tripPlanService.copyTripPlanFromTripRecord(tripRecordId, principalDetails));

        return ResponseEntity
            .status(responseBody.getCode())
            .body(responseBody);
    }

    @GetMapping("/my")
    public ResponseEntity<ResponseDTO<Page<TripPlanListReponseDto>>> getMyTripPlans(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        Pageable pageable
    ) {
        Page<TripPlanListReponseDto> responseDtos
            = tripPlanService.getMyTripPlansList(principalDetails, pageable);
        ResponseDTO<Page<TripPlanListReponseDto>> responseBody
            = ResponseDTO.okWithData(responseDtos);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(responseBody);
    }
}
