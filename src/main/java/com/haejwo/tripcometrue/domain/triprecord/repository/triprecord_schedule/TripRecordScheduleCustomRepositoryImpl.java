package com.haejwo.tripcometrue.domain.triprecord.repository.triprecord_schedule;

import com.haejwo.tripcometrue.domain.triprecord.dto.response.ModelAttribute.TripRecordScheduleImageListRequestAttribute;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord_schedule_media.TripRecordScheduleImageListResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.entity.QTripRecord;
import com.haejwo.tripcometrue.domain.triprecord.entity.QTripRecordSchedule;
import com.haejwo.tripcometrue.domain.triprecord.entity.QTripRecordScheduleImage;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class TripRecordScheduleCustomRepositoryImpl implements TripRecordScheduleCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public TripRecordScheduleCustomRepositoryImpl(EntityManager entityManager) {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<TripRecordScheduleImageListResponseDto> findScheduleImagesWithFilter(
        Pageable pageable,
        TripRecordScheduleImageListRequestAttribute request
    ) {

        QTripRecord qTripRecord = QTripRecord.tripRecord;
        QTripRecordSchedule qTripRecordSchedule = QTripRecordSchedule.tripRecordSchedule;
        QTripRecordScheduleImage qTripRecordScheduleImage = QTripRecordScheduleImage.tripRecordScheduleImage;

        List<TripRecordScheduleImageListResponseDto> result = jpaQueryFactory
            .select(Projections.constructor(TripRecordScheduleImageListResponseDto.class,
                qTripRecordSchedule.tripRecord.id,
                qTripRecordScheduleImage.imageUrl.min()))
            .from(qTripRecordSchedule)
            .leftJoin(qTripRecordSchedule.tripRecord, qTripRecord)
            .leftJoin(qTripRecordSchedule.tripRecordScheduleImages, qTripRecordScheduleImage)
            .where(qTripRecordSchedule.place.id.eq(request.placeId()))
            .groupBy(qTripRecordSchedule.tripRecord.id)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return result;
    }
}
