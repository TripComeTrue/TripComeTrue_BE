package com.haejwo.tripcometrue.domain.member.entity;

import com.haejwo.tripcometrue.domain.member.entity.rating.MilkLevel;
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
    private MilkLevel milkLevel;

    private String introduction;

    private Integer nickNameChangeCount;

    private Double memberRating;

    @Builder
    public Member(String email, String nickname, String password, String authority,
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

    public void updateMilkLevel(){
        this.milkLevel = MilkLevel.getLevelByPoint(this.totalPoint);
    }

    public void earnPoint(int point) {
        this.totalPoint += point;
        updateMilkLevel();
    }

    @PrePersist
    private void init(){
        totalPoint = (totalPoint == null) ? 0 : totalPoint;
        nickNameChangeCount = (nickNameChangeCount == null) ? 0 : nickNameChangeCount;
        updateMilkLevel();
    }
}
