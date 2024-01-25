package com.haejwo.tripcometrue.domain.alarm.entity;

import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Alarm extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "alarm_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "from_member_id")
  private Member fromMember;

  private Long windowId;
  private Long objectId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "to_member_id")
  private Member toMember;

  @Enumerated(EnumType.STRING)
  private AlarmType alarmType;

  private String alarmArgs;


  @Builder
  public Alarm(
      Member fromMember,
      Member toMember,
      AlarmType alarmType,
      Long windowId,
      Long objectId,
      String alarmArgs) {
    this.fromMember = fromMember;
    this.toMember = toMember;
    this.alarmType = alarmType;
    this.windowId = windowId;
    this.objectId = objectId;
    this.alarmArgs = alarmArgs;
  }

}
