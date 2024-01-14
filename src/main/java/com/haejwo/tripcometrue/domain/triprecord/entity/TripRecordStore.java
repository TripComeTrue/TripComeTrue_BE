//package com.haejwo.tripcometrue.domain.triprecord.entity;
//
//import com.haejwo.tripcometrue.domain.member.entity.Member;
//import com.haejwo.tripcometrue.global.entity.BaseTimeEntity;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import lombok.AccessLevel;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Getter
//@NoArgsConstructor(access = AccessLevel.PACKAGE)
//public class TripRecordStore extends BaseTimeEntity {
//
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "trip_record_store_id")
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "member_id")
//    private Member member;
//
//    @ManyToOne
//    @JoinColumn(name = "trip_record_id")
//    private TripRecord tripRecord;
//
//    @Builder
//    public TripRecordStore(Long id, Member member, TripRecord tripRecord) {
//        this.id = id;
//        this.member = member;
//        this.tripRecord = tripRecord;
//    }
//}
