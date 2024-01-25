package com.haejwo.tripcometrue.domain.alarm.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AlarmType {

  NEW_TRIP_RECORD_REVIEW("새 리뷰 알림"),
  NEW_TRIP_RECORD_COMMENT("새 여행후기 댓글 알림"),
  NEW_PLACE_REVIEW_COMMENT("새 여행지리뷰 댓글 알림"),
  REVIEW_WRITE_REQUEST("리뷰 작성 요청 알림");

  private final String message;
}
