package com.haejwo.tripcometrue.domain.alarm.controller;

import com.haejwo.tripcometrue.domain.alarm.dto.response.AlarmResponseDto;
import com.haejwo.tripcometrue.domain.alarm.service.AlarmService;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AlarmController {

  private final AlarmService alarmService;


  @GetMapping("/v1/member/alarms")
  public ResponseEntity<ResponseDTO<Page<AlarmResponseDto>>> getAlarms(
      @AuthenticationPrincipal PrincipalDetails principalDetails,
      Pageable pageable
  ) {
    Page<AlarmResponseDto> alarmsResponse = alarmService.getAlarms(principalDetails, pageable);
    return ResponseEntity.ok(ResponseDTO.okWithData(alarmsResponse));
  }
}
