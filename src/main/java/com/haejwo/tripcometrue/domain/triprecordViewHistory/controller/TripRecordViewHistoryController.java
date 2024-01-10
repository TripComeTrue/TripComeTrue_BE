package com.haejwo.tripcometrue.domain.triprecordViewHistory.controller;
import com.haejwo.tripcometrue.domain.triprecordViewHistory.dto.response.TripRecordViewHistoryResponseDto;
import com.haejwo.tripcometrue.domain.triprecordViewHistory.service.TripRecordViewHistoryService;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/trip-record")
@RequiredArgsConstructor
public class TripRecordViewHistoryController {

  private final TripRecordViewHistoryService tripRecordViewHistoryService;

  @GetMapping("/view-history")
  public ResponseEntity<ResponseDTO<Page<TripRecordViewHistoryResponseDto>>> getViewHistory(
      @AuthenticationPrincipal PrincipalDetails principalDetails,
      @PageableDefault(size = 10, sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable) {

    Page<TripRecordViewHistoryResponseDto> historyPage = tripRecordViewHistoryService.getViewHistory(principalDetails, pageable);
    ResponseDTO<Page<TripRecordViewHistoryResponseDto>> responseBody = ResponseDTO.okWithData(historyPage);

    return ResponseEntity
        .status(responseBody.getCode())
        .body(responseBody);
    }
  }
