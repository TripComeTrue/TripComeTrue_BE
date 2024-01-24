package com.haejwo.tripcometrue.domain.comment.triprecord.repository;

import com.haejwo.tripcometrue.domain.comment.triprecord.entity.TripRecordComment;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TripRecordCommentRepository extends JpaRepository<TripRecordComment, Long> {

    Slice<TripRecordComment> findByTripRecord(TripRecord tripRecord, Pageable pageable);

    @Modifying
    @Query("delete from TripRecordComment trc where trc.parentComment.id = :tripRecordCommentId")
    int deleteChildrenByTripRecordCommentId(@Param("tripRecordCommentId") Long tripRecordCommentId);

    @Modifying
    @Query("delete from TripRecordComment trc where trc.id = :tripRecordCommentId")
    int deleteParentByTripRecordCommentId(@Param("tripRecordCommentId") Long tripRecordCommentId);
}
