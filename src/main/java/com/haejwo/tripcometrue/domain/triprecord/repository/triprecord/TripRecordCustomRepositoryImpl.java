package com.haejwo.tripcometrue.domain.triprecord.repository.triprecord;

import com.haejwo.tripcometrue.domain.member.entity.QMember;
import com.haejwo.tripcometrue.domain.triprecord.dto.request.ModelAttribute.TripRecordListRequestAttribute;
import com.haejwo.tripcometrue.domain.triprecord.dto.request.ModelAttribute.TripRecordSearchParamAttribute;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.member.TripRecordMemberResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord.TripRecordListResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord_schedule_media.TripRecordHotShortsListResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.entity.*;
import com.haejwo.tripcometrue.domain.triprecord.entity.type.ExpenseRangeType;
import com.haejwo.tripcometrue.global.enums.Country;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static com.haejwo.tripcometrue.domain.city.entity.QCity.city;
import static com.haejwo.tripcometrue.domain.member.entity.QMember.member;
import static com.haejwo.tripcometrue.domain.place.entity.QPlace.place;
import static com.haejwo.tripcometrue.domain.triprecord.entity.QTripRecord.tripRecord;
import static com.haejwo.tripcometrue.domain.triprecord.entity.QTripRecordSchedule.tripRecordSchedule;
import static com.haejwo.tripcometrue.domain.triprecord.entity.QTripRecordTag.tripRecordTag;

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
                Projections.constructor(TripRecordMemberResponseDto.class, qMember.memberBase.nickname, qMember.profileImage)
            ))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return result;
    }

    @Override
    public Slice<TripRecord> findTripRecordListByFilter(
        TripRecordSearchParamAttribute requestParamAttribute,
        Pageable pageable
    ) {

        List<Long> tripRecordIds = queryFactory
            .select(tripRecord.id)
            .from(tripRecordSchedule)
            .join(tripRecordSchedule.tripRecord, tripRecord)
            .join(tripRecordSchedule.place, place)
            .join(place.city, city)
            .where(
                eqCityId(requestParamAttribute.cityId()),
                containsIgnoreCaseCityName(requestParamAttribute.cityName()),
                containsIgnoreCasePlaceName(requestParamAttribute.placeName()),
                eqOrGoeTotalDays(requestParamAttribute.totalDays())
            )
            .groupBy(tripRecord.id)
            .fetch();

        int pageSize = pageable.getPageSize();
        List<TripRecord> content = queryFactory
            .selectFrom(tripRecord)
            .join(tripRecord.member, member).fetchJoin()
            .where(
                tripRecord.id.in(tripRecordIds)
            )
            .orderBy(getSort(pageable))
            .offset(pageable.getOffset())
            .limit(pageSize + 1)
            .fetch();

        boolean hasNext = false;
        if (content.size() > pageSize) {
            content.remove(pageSize);
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Override
    public Slice<TripRecord> findTripRecordsByHashTag(String hashTag, Pageable pageable) {

        int pageSize = pageable.getPageSize();
        List<TripRecord> content = queryFactory
            .selectFrom(tripRecord)
            .where(
                tripRecord.id.in(
                    JPAExpressions.select(tripRecord.id)
                        .from(tripRecordTag)
                        .join(tripRecordTag.tripRecord, tripRecord)
                        .where(
                            containsIgnoreCaseHashTag(hashTag)
                        )
                        .groupBy(tripRecord.id)
                )
            )
            .orderBy(getSort(pageable))
            .offset(pageable.getOffset())
            .limit(pageSize + 1)
            .fetch();

        boolean hasNext = false;
        if (content.size() > pageSize) {
            content.remove(pageSize);
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Override
    public List<TripRecord> findTopTripRecordListDomestic(int size) {

        return queryFactory
            .selectFrom(tripRecord)
            .where(tripRecord.countries.containsIgnoreCase(Country.KOREA.name()))
            .orderBy(tripRecord.averageRating.desc(), tripRecord.storeCount.desc())
            .limit(size)
            .fetch();
    }

    @Override
    public List<TripRecord> findTopTripRecordListOverseas(int size) {

        return queryFactory
            .selectFrom(tripRecord)
            .where(tripRecord.countries.containsIgnoreCase(Country.KOREA.name()).not())
            .orderBy(tripRecord.averageRating.desc(), tripRecord.storeCount.desc())
            .limit(size)
            .fetch();
    }

    @Override
    public List<TripRecordHotShortsListResponseDto> findTripRecordHotShortsList(Pageable pageable) {

        QTripRecord qTripRecord = QTripRecord.tripRecord;
        QTripRecordSchedule qTripRecordSchedule = QTripRecordSchedule.tripRecordSchedule;
        QTripRecordScheduleVideo qTripRecordScheduleVideo = QTripRecordScheduleVideo.tripRecordScheduleVideo;
        QMember qMember = QMember.member;

        List<TripRecordHotShortsListResponseDto> result = queryFactory
            .select(Projections.constructor(TripRecordHotShortsListResponseDto.class,
                qTripRecord.id,
                qTripRecord.title,
                qTripRecord.storeCount,
                JPAExpressions
                    .select(qTripRecordScheduleVideo.id.min())
                    .from(qTripRecordScheduleVideo)
                    .where(qTripRecordScheduleVideo.tripRecordSchedule.id.eq(
                        JPAExpressions
                            .select(qTripRecordSchedule.id.min())
                            .from(qTripRecordSchedule)
                            .where(qTripRecordSchedule.tripRecord.id.eq(qTripRecord.id)))),
                JPAExpressions
                    .select(qTripRecordScheduleVideo.thumbnailUrl.min())
                    .from(qTripRecordScheduleVideo)
                    .where(qTripRecordScheduleVideo.tripRecordSchedule.tripRecord.id.eq(qTripRecord.id)),
                Projections.constructor(TripRecordMemberResponseDto.class,
                    qMember.memberBase.nickname,
                    qMember.profileImage)))
            .from(qTripRecord)
            .leftJoin(qTripRecord.tripRecordSchedules, qTripRecordSchedule)
            .leftJoin(qTripRecordSchedule.tripRecordScheduleVideos, qTripRecordScheduleVideo)
            .leftJoin(qTripRecord.member, qMember)
            .where(qTripRecordScheduleVideo.thumbnailUrl.isNotNull())
            .groupBy(qTripRecord.id)
            .orderBy(qTripRecord.storeCount.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return result;
    }


    @Override
    public List<TripRecord> findTripRecordListInMemberIds(List<Long> memberIds) {

        return queryFactory.selectFrom(tripRecord)
            .join(tripRecord.member, member)
            .where(member.id.in(memberIds))
            .orderBy(tripRecord.storeCount.desc(), tripRecord.createdAt.desc())
            .fetch();
    }

    @Override
    public List<TripRecord> findTripRecordListWithMemberInMemberIds(List<Long> memberIds) {

        return queryFactory.selectFrom(tripRecord)
            .join(tripRecord.member, member).fetchJoin()
            .where(member.id.in(memberIds))
            .orderBy(tripRecord.storeCount.desc(), tripRecord.createdAt.desc())
            .fetch();
    }

    private BooleanExpression eqCityId(Long cityId) {
        return Objects.nonNull(cityId) ? city.id.eq(cityId) : null;
    }

    private BooleanExpression containsIgnoreCaseCityName(String cityName) {
        if (!StringUtils.hasText(cityName)) {
            return null;
        }

        String replacedWhitespace = cityName.replaceAll(" ", "");

        return Expressions.stringTemplate(
            "function('replace',{0},{1},{2})", city.name, " ", ""
        ).containsIgnoreCase(replacedWhitespace);
    }

    private BooleanExpression containsIgnoreCasePlaceName(String placeName) {
        if (!StringUtils.hasText(placeName)) {
            return null;
        }

        String replacedWhitespace = placeName.replaceAll(" ", "");

        return Expressions.stringTemplate(
            "function('replace',{0},{1},{2})", place.name, " ", ""
        ).containsIgnoreCase(replacedWhitespace);
    }

    private BooleanExpression containsIgnoreCaseHashTag(String hashTag) {
        if (!StringUtils.hasText(hashTag)) {
            return null;
        }

        String replacedWhitespace = hashTag.replaceAll(" ", "");

        return Expressions.stringTemplate(
            "function('replace',{0},{1},{2})", tripRecordTag.hashTagType, " ", ""
        ).containsIgnoreCase(replacedWhitespace);
    }

    private BooleanExpression eqOrGoeTotalDays(String totalDays) {
        if (!StringUtils.hasText(totalDays)) {
            return null;
        }

        if (totalDays.equalsIgnoreCase("etc")) {
            return tripRecord.totalDays.goe(5);
        }

        return tripRecord.totalDays.eq(Integer.parseInt(totalDays));
    }

    private OrderSpecifier<?>[] getSort(Pageable pageable) {

        List<OrderSpecifier<?>> orderSpecifiers = new LinkedList<>();
        if (!pageable.getSort().isEmpty()) {
            for (Sort.Order sortOrder : pageable.getSort()) {
                Order direction = sortOrder.getDirection().isAscending() ? Order.ASC : Order.DESC;

                String property = sortOrder.getProperty();
                switch (property) {
                    case "id":
                        orderSpecifiers.add(new OrderSpecifier<>(direction, tripRecord.id));
                        break;
                    case "createdAt":
                        orderSpecifiers.add(new OrderSpecifier<>(direction, tripRecord.createdAt));
                        break;
                    case "storeCount":
                        orderSpecifiers.add(new OrderSpecifier<>(direction, tripRecord.storeCount));
                        break;
                    case "averageRating":
                        orderSpecifiers.add(new OrderSpecifier<>(direction, tripRecord.averageRating));
                        break;

                    // TODO: 정렬 기준 예외 처리
                }
            }
        }

        return orderSpecifiers.isEmpty() ? null : orderSpecifiers.toArray(OrderSpecifier[]::new); // 최신순
    }
}
