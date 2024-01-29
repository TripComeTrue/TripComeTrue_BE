package com.haejwo.tripcometrue.domain.review.placereview.repository;

import com.haejwo.tripcometrue.domain.place.entity.Place;
import com.haejwo.tripcometrue.domain.review.placereview.entity.PlaceReview;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.haejwo.tripcometrue.domain.review.placereview.entity.QPlaceReview.placeReview;

public class PlaceReviewRepositoryCustomImpl implements PlaceReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PlaceReviewRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<PlaceReview> findByPlace(Place place, boolean onlyImage, Pageable pageable) {

        List<PlaceReview> content = queryFactory
                .selectFrom(placeReview)
                .join(placeReview.place).on(placeReview.place.id.eq(place.getId()))
                .where(imageIsNotNull(onlyImage))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrderSpecifier(pageable.getSort()))
                .fetch();

        JPAQuery<Long> count = queryFactory
                .select(placeReview.count())
                .from(placeReview)
                .where(placeReview.place.id.eq(place.getId()), imageIsNotNull(onlyImage));

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    private BooleanExpression imageIsNotNull(boolean onlyImage) {
        if (onlyImage) {
            return placeReview.imageUrl.isNotNull();
        }
        return null;
    }

    private OrderSpecifier<?>[] getOrderSpecifier(Sort sort) {
        return sort.stream()
                .map(order -> {
                    PathBuilder<PlaceReview> entityPath = new PathBuilder<>(PlaceReview.class, "placeReview");
                    String property = order.getProperty();
                    OrderSpecifier<?> orderSpecifier = order.isAscending() ?
                            entityPath.getString(property).asc() :
                            entityPath.getString(property).desc();
                    return orderSpecifier;
                })
                .toArray(OrderSpecifier[]::new);
    }
}
