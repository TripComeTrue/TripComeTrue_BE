package com.haejwo.tripcometrue.domain.triprecord.controller;

import com.haejwo.tripcometrue.domain.triprecord.dto.request.TripRecordRequestDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.TripRecordResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.service.TripRecordService;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/trip-record")
@RequiredArgsConstructor
public class TripRecordController {

    private final TripRecordService tripRecordService;

    @PostMapping
    public ResponseEntity<ResponseDTO<TripRecordResponseDto>> tripRecordAdd(
        @RequestBody TripRecordRequestDto requestDto
    ) {

        TripRecordResponseDto responseDto = tripRecordService.addTripRecord(requestDto);
        ResponseDTO<TripRecordResponseDto> responseBody = ResponseDTO.okWithData(responseDto);

        return ResponseEntity
            .status(responseBody.getCode())
            .body(responseBody);
    }

    @GetMapping("/{tripRecordId}")
    public ResponseEntity<ResponseDTO<TripRecordResponseDto>> tripRecordDetails(
        @PathVariable Long tripRecordId
    ) {

        TripRecordResponseDto responseDto = tripRecordService.findTripRecord(tripRecordId);
        ResponseDTO<TripRecordResponseDto> responseBody = ResponseDTO.okWithData(responseDto);

        return ResponseEntity
            .status(responseBody.getCode())
            .body(responseBody);
    }

    @PutMapping("/{tripRecordId}")
    public ResponseEntity<ResponseDTO<TripRecordResponseDto>> tripRecordModify(
        @PathVariable Long tripRecordId,
        @RequestBody TripRecordRequestDto requestDto
    ) {

        TripRecordResponseDto responseDto = tripRecordService.modifyTripRecord(tripRecordId, requestDto);
        ResponseDTO<TripRecordResponseDto> responseBody = ResponseDTO.okWithData(responseDto);

        return ResponseEntity
            .status(responseBody.getCode())
            .body(responseBody);

    }

    @DeleteMapping("/{tripRecordId}")
    public ResponseEntity<ResponseDTO> tripRecordRemove(
        @PathVariable Long tripRecordId
    ) {

        tripRecordService.removeTripRecord(tripRecordId);
        ResponseDTO responseBody = ResponseDTO.ok();

        return ResponseEntity
            .status(responseBody.getCode())
            .body(responseBody);
    }





}
