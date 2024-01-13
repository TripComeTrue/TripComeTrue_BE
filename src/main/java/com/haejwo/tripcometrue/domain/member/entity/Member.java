package com.haejwo.tripcometrue.domain.member.entity;

import com.haejwo.tripcometrue.global.entity.BaseTimeEntity;
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

    private Double member_rating;

    private String introduction;

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

    public void earnPoint(int point) {
        this.total_point += point;
    }

    @PrePersist
    private void init() {
        total_point = 0;
    }
}
