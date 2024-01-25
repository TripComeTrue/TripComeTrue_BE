package com.haejwo.tripcometrue.domain.alarm.service;

import com.haejwo.tripcometrue.domain.alarm.dto.response.AlarmResponseDto;
import com.haejwo.tripcometrue.domain.alarm.entity.Alarm;
import com.haejwo.tripcometrue.domain.alarm.entity.AlarmType;
import com.haejwo.tripcometrue.domain.alarm.repository.AlarmRepository;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Transactional
public class AlarmService {

  private final AlarmRepository alarmRepository;


  public Page<AlarmResponseDto> getAlarms(PrincipalDetails principalDetails, Pageable pageable) {
    Page<Alarm> alarms = alarmRepository.findAllByToMember(principalDetails.getMember(), pageable);
    return alarms.map(AlarmResponseDto::fromEntity);
  }

  public void addAlarm(Member fromMember, Member toMember, AlarmType alarmType, Long windowId,
      Long objectId) {
    String alarmArgs = createAlarmArgs(fromMember.getMemberBase().getNickname(), alarmType);
    Alarm alarm = Alarm.builder()
        .fromMember(fromMember)
        .toMember(toMember)
        .alarmType(alarmType)
        .windowId(windowId)
        .objectId(objectId)
        .alarmArgs(alarmArgs)
        .build();
    alarmRepository.save(alarm);
  }

  private String createAlarmArgs(String memberNickname, AlarmType alarmType) {
    switch (alarmType) {
      case NEW_TRIP_RECORD_REVIEW:
        return memberNickname + "님이 여행 후기에 리뷰를 남기셨습니다.";
      case NEW_TRIP_RECORD_COMMENT:
        return memberNickname + "님이 여행후기에 댓글을 남기셨습니다.";
      case NEW_PLACE_REVIEW_COMMENT:
        return memberNickname + "님이 여행지리뷰에 댓글을 남기셨습니다.";
      case REVIEW_WRITE_REQUEST:
        return "여행 후기에 리뷰를 남겨주세요.";
      default:
        return "알림 타입이 지정되지 않았습니다.";
    }
  }
}
