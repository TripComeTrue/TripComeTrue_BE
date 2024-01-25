package com.haejwo.tripcometrue.domain.alarm.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haejwo.tripcometrue.domain.alarm.entity.Alarm;
import com.haejwo.tripcometrue.domain.alarm.entity.AlarmType;
import java.time.LocalDateTime;

public record AlarmResponseDto(
    Long alarmId,
    String alarmArgs,
    String fromMemberNickname,
    Long windowId,
    Long objectId,
    AlarmType alarmType,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH-mm-ss")
    LocalDateTime createdAt
) {

  public static AlarmResponseDto fromEntity(Alarm alarm) {
    return new AlarmResponseDto(
        alarm.getId(),
        alarm.getAlarmArgs(),
        alarm.getFromMember().getMemberBase().getNickname(),
        alarm.getWindowId(),
        alarm.getObjectId(),
        alarm.getAlarmType(),
        alarm.getCreatedAt()
    );
  }
}