package com.haejwo.tripcometrue.domain.triprecord.repository.triprecord;

import com.haejwo.tripcometrue.domain.triprecord.dto.response.ModelAttribute.TripRecordListRequestAttribute;
import com.haejwo.tripcometrue.domain.triprecord.entity.QTripRecord;
import com.haejwo.tripcometrue.domain.triprecord.entity.QTripRecordSchedule;
import com.haejwo.tripcometrue.domain.triprecord.entity.QTripRecordTag;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import com.haejwo.tripcometrue.domain.triprecord.entity.type.ExpenseRangeType;
import com.haejwo.tripcometrue.global.enums.Country;
import com.querydsl.core.BooleanBuilder;
import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class TripRecordCustomRepositoryImpl extends QuerydslRepositorySupport implements TripRecordCustomRepository {

    private final JPAQueryFactory queryFactory;

    public TripRecordCustomRepositoryImpl(JPAQueryFactory queryFactory) {
        super(TripRecord.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public List<TripRecord> findTripRecordWithFilter(
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
            booleanBuilder.and(qTripRecordSchedule.place.id.eq(request.placeId()));
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

    @Override
    public List<TripRecord> findTopTripRecordListDomestic(int size) {
        QTripRecord tripRecord = QTripRecord.tripRecord;

        return queryFactory
            .selectFrom(tripRecord)
            .where(tripRecord.countries.containsIgnoreCase(Country.KOREA.name()))
            .orderBy(tripRecord.averageRating.desc(), tripRecord.storeCount.desc())
            .limit(size)
            .fetch();
    }

    @Override
    public List<TripRecord> findTopTripRecordListOverseas(int size) {
        QTripRecord tripRecord = QTripRecord.tripRecord;

        return queryFactory
            .selectFrom(tripRecord)
            .where(tripRecord.countries.containsIgnoreCase(Country.KOREA.name()).not())
            .orderBy(tripRecord.averageRating.desc(), tripRecord.storeCount.desc())
            .limit(size)
            .fetch();
    }
}
