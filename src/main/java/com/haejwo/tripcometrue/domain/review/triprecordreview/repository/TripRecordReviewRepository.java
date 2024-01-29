package com.haejwo.tripcometrue.domain.review.triprecordreview.repository;

import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.review.triprecordreview.entity.TripRecordReview;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TripRecordReviewRepository extends JpaRepository<TripRecordReview, Long>, TripRecordReviewRepositoryCustom {

    @Query("select trr from TripRecordReview trr join fetch trr.member m where trr.member = :member and trr.content is not null order by trr.createdAt desc")
    Page<TripRecordReview> findByMember(@Param("member") Member member, Pageable pageable);

    @Query("select trr from TripRecordReview trr where trr.member = :member and trr.tripRecord.id = :tripRecordId")
    Optional<TripRecordReview> findByMemberAndTripRecordId(@Param("member") Member member, @Param("tripRecordId") Long tripRecordId);

    @Query("select trr from TripRecordReview trr where trr.tripRecord.id = :tripRecordId and trr.content is not null order by trr.createdAt desc limit 1")
    Optional<TripRecordReview> findTopByTripRecordIdOrderByCreatedAtDesc(@Param("tripRecordId") Long tripRecordId);

    boolean existsByMemberAndTripRecord(Member member, TripRecord tripRecord);

    @Query("select count(trr) from TripRecordReview trr where trr.content is not null")
    Long countByTripRecordId(Long tripRecordId);
}
