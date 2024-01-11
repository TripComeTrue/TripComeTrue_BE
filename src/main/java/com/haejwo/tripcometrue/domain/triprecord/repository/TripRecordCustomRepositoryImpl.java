package com.haejwo.tripcometrue.domain.triprecord.repository;

import com.haejwo.tripcometrue.domain.triprecord.dto.TripRecordListRequestAttribute;
import com.haejwo.tripcometrue.domain.triprecord.entity.QTripRecord;
import com.haejwo.tripcometrue.domain.triprecord.entity.QTripRecordSchedule;
import com.haejwo.tripcometrue.domain.triprecord.entity.QTripRecordTag;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import com.haejwo.tripcometrue.domain.triprecord.entity.type.ExpenseRangeType;
import com.querydsl.core.BooleanBuilder;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class TripRecordCustomRepositoryImpl extends QuerydslRepositorySupport implements TripRecordCustomRepository {

    public TripRecordCustomRepositoryImpl() {
        super(TripRecord.class);
    }

    @Override
    public List<TripRecord> finTripRecordWithFilter(
        Pageable pageable,
        TripRecordListRequestAttribute request
    ) {

        QTripRecord qTripRecord = QTripRecord.tripRecord;
        QTripRecordTag qTripRecordTag = QTripRecordTag.tripRecordTag;
        QTripRecordSchedule qTripRecordSchedule = QTripRecordSchedule.tripRecordSchedule;

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // hashtag
        if(request.hashtag() != null) {
            booleanBuilder.and(qTripRecordTag.hashTagType.eq(request.hashtag()));
        }
        // placeId
        if(request.placeId() != null) {
            booleanBuilder.and(qTripRecordSchedule.placeId.eq(request.placeId()));
        }

        // expenseRangeType
        if(request.expenseRangeType() != null) {
            ExpenseRangeType expenseRangeType = ExpenseRangeType.findByMax(request.expenseRangeType());
            booleanBuilder.and(qTripRecord.expenseRangeType.eq(expenseRangeType));
        }

        // totalDays
        if(request.totalDays() != null) {
            booleanBuilder.and(qTripRecord.totalDays.eq(request.totalDays()));
        }

        List<TripRecord> result = from(qTripRecord)
            .leftJoin(qTripRecord.tripRecordTags, qTripRecordTag)
            .leftJoin(qTripRecord.tripRecordSchedules, qTripRecordSchedule)
            .where(booleanBuilder)
            .fetch();

        return result;
    }
}
