package com.haejwo.tripcometrue.domain.comment.triprecord.repository;

import com.haejwo.tripcometrue.domain.comment.triprecord.entity.TripRecordComment;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRecordCommentRepository extends JpaRepository<TripRecordComment, Long> {

    Page<TripRecordComment> findByTripRecord(TripRecord tripRecord, Pageable pageable);
}
