package com.haejwo.tripcometrue.domain.triprecord.repository.triprecord;

import com.haejwo.tripcometrue.domain.member.entity.QMember;
import com.haejwo.tripcometrue.domain.triprecord.dto.request.ModelAttribute.TripRecordListRequestAttribute;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.member.TripRecordMemberResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord.TripRecordListResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.entity.QTripRecord;
import com.haejwo.tripcometrue.domain.triprecord.entity.QTripRecordImage;
import com.haejwo.tripcometrue.domain.triprecord.entity.QTripRecordSchedule;
import com.haejwo.tripcometrue.domain.triprecord.entity.QTripRecordTag;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import com.haejwo.tripcometrue.domain.triprecord.entity.type.ExpenseRangeType;
import com.haejwo.tripcometrue.global.enums.Country;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class TripRecordCustomRepositoryImpl extends QuerydslRepositorySupport implements TripRecordCustomRepository {

    private final JPAQueryFactory queryFactory;

    public TripRecordCustomRepositoryImpl(JPAQueryFactory queryFactory) {
        super(TripRecord.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public List<TripRecordListResponseDto> finTripRecordWithFilter(
        Pageable pageable,
        TripRecordListRequestAttribute request
    ) {

        QTripRecord qTripRecord = QTripRecord.tripRecord;
        QTripRecordTag qTripRecordTag = QTripRecordTag.tripRecordTag;
        QTripRecordSchedule qTripRecordSchedule = QTripRecordSchedule.tripRecordSchedule;
        QTripRecordImage qTripRecordImage = QTripRecordImage.tripRecordImage;
        QMember qMember = QMember.member;

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

        OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(Order.ASC, qTripRecord.id);

        if (request.orderBy() != null) {
            switch (request.orderBy()) {
                case "averageRating":
                    orderSpecifier = new OrderSpecifier<>(request.order(), qTripRecord.averageRating);
                    break;
                case "viewCount":
                    orderSpecifier = new OrderSpecifier<>(request.order(), qTripRecord.viewCount);
                    break;
                case "storeCount":
                    orderSpecifier = new OrderSpecifier<>(request.order(), qTripRecord.storeCount);
                    break;
                case "reviewCount":
                    orderSpecifier = new OrderSpecifier<>(request.order(), qTripRecord.reviewCount);
                    break;
                case "commentCount":
                    orderSpecifier = new OrderSpecifier<>(request.order(), qTripRecord.commentCount);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid orderBy parameter: " + request.orderBy()); // TODO: 예외처리 만들기
            }
        }

        List<TripRecordListResponseDto> result = from(qTripRecord)
            .leftJoin(qTripRecord.tripRecordTags, qTripRecordTag)
            .leftJoin(qTripRecord.tripRecordSchedules, qTripRecordSchedule)
            .leftJoin(qTripRecord.tripRecordImages, qTripRecordImage)
            .join(qTripRecord.member, qMember)
            .where(booleanBuilder)
            .groupBy(qTripRecord)
            .orderBy(orderSpecifier)
            .select(Projections.constructor(TripRecordListResponseDto.class,
                qTripRecord.id,
                qTripRecord.title,
                qTripRecord.countries,
                qTripRecord.totalDays,
                qTripRecord.commentCount,
                qTripRecord.storeCount,
                JPAExpressions
                    .select(qTripRecordImage.imageUrl.min())
                    .from(qTripRecordImage)
                    .where(qTripRecordImage.tripRecord.id.eq(qTripRecord.id)),
                Projections.constructor(TripRecordMemberResponseDto.class, qMember.memberBase.nickname, qMember.profile_image)
            ))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
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
