package com.haejwo.tripcometrue.domain.triprecord.controller;

import com.haejwo.tripcometrue.domain.triprecord.service.TripRecordScheduleVideoService;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import com.haejwo.tripcometrue.global.validator.annotation.HomeVideoListQueryType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v1/schedule-videos")
@RestController
public class TripRecordScheduleVideoController {

    private final TripRecordScheduleVideoService tripRecordScheduleVideoService;

    @GetMapping("/list")
    public ResponseEntity<ResponseDTO<?>> getVideosByType(
        @RequestParam("type") @HomeVideoListQueryType String type
    ) {
        return ResponseEntity
            .ok()
            .body(
                ResponseDTO.okWithData(
                    tripRecordScheduleVideoService.getNewestVideos(type)
                )
            );
    }
}
