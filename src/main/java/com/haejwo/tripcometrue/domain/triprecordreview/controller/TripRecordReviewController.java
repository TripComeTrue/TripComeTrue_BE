package com.haejwo.tripcometrue.domain.triprecordreview.controller;

import com.haejwo.tripcometrue.domain.triprecordreview.request.TripRecordReviewRegisterRequest;
import com.haejwo.tripcometrue.domain.triprecordreview.service.TripRecordReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TripRecordReviewController {

    private final TripRecordReviewService tripRecordReviewService;

    @PostMapping("/v1/trip-record-reviews/{tripRecordReviewId}")
    public ResponseEntity<> registerTripRecordReview(
            @PathVariable Long tripRecordReviewId,
            @RequestBody @Valid TripRecordReviewRegisterRequest request) {
        tripRecordReviewService.saveTripRecordReview(tripRecordReviewId, request);
        return new ResponseEntity()
    }

}
