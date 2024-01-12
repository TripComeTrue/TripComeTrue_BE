package com.haejwo.tripcometrue.domain.triprecord.repository.triprecord_schedule_image;

import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordScheduleImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRecordScheduleImageRepository extends
    JpaRepository<TripRecordScheduleImage, Long>, TripRecordScheduleImageRepositoryCustom {

}
