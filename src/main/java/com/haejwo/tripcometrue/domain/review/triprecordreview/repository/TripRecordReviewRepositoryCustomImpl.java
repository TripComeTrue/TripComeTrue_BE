package com.haejwo.tripcometrue.domain.review.triprecordreview.repository;

import com.haejwo.tripcometrue.domain.review.triprecordreview.entity.TripRecordReview;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.haejwo.tripcometrue.domain.review.triprecordreview.entity.QTripRecordReview.tripRecordReview;

public class TripRecordReviewRepositoryCustomImpl implements TripRecordReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public TripRecordReviewRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<TripRecordReview> findByTripRecord(TripRecord tripRecord, Pageable pageable) {

        List<TripRecordReview> content = queryFactory
                .selectFrom(tripRecordReview)
                .join(tripRecordReview.tripRecord).on(tripRecordReview.tripRecord.id.eq(tripRecord.getId()))
                .where(tripRecordReview.content.isNotNull())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrderSpecifier(pageable.getSort()))
                .fetch();

        JPAQuery<Long> count = queryFactory
                .select(tripRecordReview.count())
                .from(tripRecordReview)
                .join(tripRecordReview.tripRecord).on(tripRecordReview.tripRecord.id.eq(tripRecord.getId()))
                .where(tripRecordReview.content.isNotNull()
                );

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    private OrderSpecifier<?>[] getOrderSpecifier(Sort sort) {
        return sort.stream()
                .map(order -> {
                    PathBuilder<TripRecordReview> entityPath = new PathBuilder<>(TripRecordReview.class, "tripRecordReview");
                    String property = order.getProperty();
                    OrderSpecifier<?> orderSpecifier = order.isAscending() ?
                            entityPath.getString(property).asc() :
                            entityPath.getString(property).desc();
                    return orderSpecifier;
                })
                .toArray(OrderSpecifier[]::new);
    }
}
