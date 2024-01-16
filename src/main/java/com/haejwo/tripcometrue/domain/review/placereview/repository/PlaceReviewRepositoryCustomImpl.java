package com.haejwo.tripcometrue.domain.review.placereview.repository;

import com.haejwo.tripcometrue.domain.review.placereview.entity.PlaceReview;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.haejwo.tripcometrue.domain.review.placereview.entity.QPlaceReview.placeReview;

public class PlaceReviewRepositoryCustomImpl implements PlaceReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PlaceReviewRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<PlaceReview> findByPlaceId(Long placeId, Pageable pageable) {
        List<PlaceReview> result = queryFactory
                .selectFrom(placeReview)
                .join(placeReview.place)
                .on(placeReview.place.id.eq(placeId))
                .where()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(result, pageable, 5999);
    }


//    @Override
//    public Page<PlaceReview> findByPlaceId(Long placeId, boolean includeImage, Pageable pageable) {
//        List<PlaceReview> content = queryFactory
//                .selectFrom(placeReview)
//                .leftJoin(placeReview.place)
//                .on(placeReview.place.id.eq(placeId))
//                .where(includeImage(includeImage))
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//
//        JPAQuery<Long> count = queryFactory
//                .select(placeReview.count())
//                .from(placeReview)
//                .where(placeReview.place.id.eq(placeId), includeImage(includeImage));
//
//        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
//    }
//
//    private BooleanExpression includeImage(boolean includeImage) {
//        if (includeImage) {
//            return placeReview.imageUrl.isNotNull();
//        }
//        return placeReview.imageUrl.isNull();
//    }
}
