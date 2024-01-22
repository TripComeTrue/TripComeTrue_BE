package com.haejwo.tripcometrue.domain.comment.triprecord.repository;

import com.haejwo.tripcometrue.domain.comment.triprecord.entity.TripRecordComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRecordCommentRepository extends JpaRepository<TripRecordComment, Long> {
}
