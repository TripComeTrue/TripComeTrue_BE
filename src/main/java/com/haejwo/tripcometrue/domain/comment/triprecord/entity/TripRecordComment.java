package com.haejwo.tripcometrue.domain.comment.triprecord.entity;

import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import com.haejwo.tripcometrue.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TripRecordComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "trip_record_id")
    private TripRecord tripRecord;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_comment_id")
    private TripRecordComment parentComment;

    @OneToMany(mappedBy = "parentComment", orphanRemoval = true)
    private List<TripRecordComment> childComments = new ArrayList<>();

    @Column(nullable = false)
    private String content;

    @Builder
    public TripRecordComment(Member member, TripRecord tripRecord, String content) {
        this.member = member;
        this.tripRecord = tripRecord;
        this.content = content;
    }
}
