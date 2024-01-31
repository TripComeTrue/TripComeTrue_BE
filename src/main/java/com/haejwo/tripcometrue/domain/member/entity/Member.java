package com.haejwo.tripcometrue.domain.member.entity;

import com.haejwo.tripcometrue.domain.member.entity.tripLevel.TripLevel;
import com.haejwo.tripcometrue.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Embedded
    protected MemberBase memberBase;

    private String provider;

    private String profileImage;

    private Integer totalPoint;

    @Enumerated(EnumType.STRING)
    private TripLevel tripLevel;

    private String introduction;

    private Integer nickNameChangeCount;

    private Double memberRating;

    private LocalDateTime nickNameChangeTime;

    @Builder
    private Member(String email, String nickname, String password, String authority,
        String provider, Double memberRating) {
        this.memberBase = new MemberBase(email, nickname, password, authority);
        this.provider = provider;
        this.memberRating = Objects.isNull(memberRating) ? 0.0 : memberRating;
    }

    public void updateProfileImage(String profileImage){
        this.profileImage = profileImage;
    }

    public void updateIntroduction(String introduction){
        this.introduction = introduction;
    }

    public void updateNickNameChangeCount(){
        this.nickNameChangeCount = (this.nickNameChangeCount == null) ? 1 : this.nickNameChangeCount + 1;
    }

    public void updateNickNameChangeTime(LocalDateTime nicknameChangeTime){
        this.nickNameChangeTime = nicknameChangeTime;
    }

    public void updateTripLevel(){
        this.tripLevel = TripLevel.getLevelByPoint(this.totalPoint);
    }

    public void earnPoint(int point) {
        this.totalPoint += point;
        updateTripLevel();
    }

    @PrePersist
    private void init(){
        totalPoint = (totalPoint == null) ? 0 : totalPoint;
        nickNameChangeCount = (nickNameChangeCount == null) ? 0 : nickNameChangeCount;
        tripLevel = (tripLevel == null) ? TripLevel.BEGINNER : tripLevel;
        profileImage = (profileImage == null) ? "https://i.imgur.com/PWZeQcP.png" : profileImage;   //임시 디폴트프로필 이미지 데이터

        updateTripLevel();
    }
}
