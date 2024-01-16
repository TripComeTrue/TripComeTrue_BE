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
import jakarta.persistence.*;
import jakarta.persistence.PrePersist;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private String profile_image;

    private Integer total_point;

    @Enumerated(EnumType.STRING)
    private MilkLevel milk_level;

    private String introduction;

    private Integer nickNameChangeCount;

//    private double member_rating;

    @Builder
    public Member(String email, String nickname, String password, String authority,
        String provider) {
        this.memberBase = new MemberBase(email, nickname, password, authority);
        this.provider = provider;
    }

    public void updateProfileImage(String profileImage){
        this.profile_image = profileImage;
    }

    public void updateIntroduction(String introduction){
        this.introduction = introduction;
    }

    public void updateNickNameChangeCount(){
        this.nickNameChangeCount = (this.nickNameChangeCount == null) ? 1 : this.nickNameChangeCount + 1;
    }

    public void updateMilkLevel(){
        this.milk_level= MilkLevel.getLevelByPoint(this.total_point);
    }

    public void earnPoint(int point) {
        this.total_point += point;
        updateMilkLevel();
    }

    @PrePersist
    private void init(){
        total_point = (total_point == null) ? 0 : total_point;
        nickNameChangeCount = (nickNameChangeCount == null) ? 0 : nickNameChangeCount;
        updateMilkLevel();
    }
}
