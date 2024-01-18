package com.haejwo.tripcometrue.domain.triprecord.controller;

import com.haejwo.tripcometrue.domain.triprecord.dto.request.ModelAttribute.TripRecordScheduleImageListRequestAttribute;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord_schedule_media.TripRecordScheduleImageListResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.service.TripRecordScheduleService;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/v1/trip-records-schedules")
public class TripRecordScheduleController {

    private final TripRecordScheduleService tripRecordScheduleService;

    @GetMapping
    public ResponseEntity<ResponseDTO<List<TripRecordScheduleImageListResponseDto>>> tripRecordScheduleImageList(
        Pageable pageable,
        @ModelAttribute TripRecordScheduleImageListRequestAttribute requestParam
    ) {

        List<TripRecordScheduleImageListResponseDto> responseDtos = tripRecordScheduleService.findScheduleImages(pageable, requestParam);

        ResponseDTO<List<TripRecordScheduleImageListResponseDto>> responseBody = ResponseDTO.okWithData(responseDtos);

        return ResponseEntity
            .status(responseBody.getCode())
            .body(responseBody);
    }

}
