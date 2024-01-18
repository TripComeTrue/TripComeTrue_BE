package com.haejwo.tripcometrue.domain.place.repositroy;

import com.haejwo.tripcometrue.domain.city.entity.City;
import com.haejwo.tripcometrue.domain.city.entity.QCity;
import com.haejwo.tripcometrue.domain.place.entity.Place;
import com.haejwo.tripcometrue.domain.place.entity.QPlace;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class PlaceCustomRepositoryImpl extends QuerydslRepositorySupport implements PlaceCustomRepository {

    private final JPAQueryFactory queryFactory;

    public PlaceCustomRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Place.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<Place> findPlaceWithFilter(Pageable pageable, Integer storedCount) {

        QPlace place = QPlace.place;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(storedCount != null && storedCount >= 0) {
            booleanBuilder.and(place.storedCount.goe(storedCount));
        }

        List<Place> result = from(place)
                                .where(booleanBuilder)
                                .offset(pageable.getOffset())
                                .limit(pageable.getPageSize())
                                .fetch();

        // 프론트의 Page 정보 필요 유무에 따라 응답 객체 List, Page 나뉨
        long total = from(place).where(booleanBuilder).fetchCount();

        return new PageImpl<>(result, pageable, total);

    }

    @Override
    public Slice<Place> findPlacesByCityId(Long cityId, Pageable pageable) {
        QPlace place = QPlace.place;
        QCity city = QCity.city;

        int pageSize = pageable.getPageSize();
        List<Place> content = queryFactory
            .selectFrom(place)
            .join(place.city, city)
            .where(city.id.eq(cityId))
            .offset(pageable.getOffset())
            .limit(pageSize + 1)
            .orderBy(getSort(pageable))
            .fetch();

        boolean hasNext = false;
        if (content.size() > pageSize) {
            content.remove(pageSize);
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Override
    public List<Place> findPlacesByCityAndOrderByStoredCountLimitSize(City city, int size) {

        QPlace place = QPlace.place;

        return queryFactory
            .selectFrom(place)
            .join(place.city)
            .where(place.city.eq(city))
            .orderBy(place.storedCount.desc(), place.commentCount.desc())
            .limit(size)
            .fetch();
    }

    private OrderSpecifier<?>[] getSort(Pageable pageable) {
        QPlace place = QPlace.place;

        OrderSpecifier<?> byStoredCount = new OrderSpecifier<>(Order.DESC, place.storedCount);
        OrderSpecifier<?> byCommentCount = new OrderSpecifier<>(Order.DESC, place.commentCount);

        //서비스에서 보내준 Pageable 객체에 정렬조건 null 값 체크
        if (!pageable.getSort().isEmpty()) {
            //정렬값이 들어 있으면 for 사용하여 값을 가져온다
            for (Sort.Order sortOrder : pageable.getSort()) {
                // 서비스에서 넣어준 DESC or ASC 를 가져온다.
                com.querydsl.core.types.Order direction = sortOrder.getDirection().isAscending() ? com.querydsl.core.types.Order.ASC : com.querydsl.core.types.Order.DESC;
                // 서비스에서 넣어준 정렬 조건을 스위치 케이스 문을 활용하여 셋팅하여 준다.
                String property = sortOrder.getProperty();
                switch (property) {
                    case "id":
                        return new OrderSpecifier[] {new OrderSpecifier<>(direction, place.id)};
                    case "createdAt":
                        return new OrderSpecifier[] {new OrderSpecifier<>(direction, place.createdAt)};
                    case "storedCount":
                        return new OrderSpecifier[] {byStoredCount, byCommentCount};
                    case "commentCount":
                        return new OrderSpecifier[] {byCommentCount, byStoredCount};
                }
            }
        }

        return new OrderSpecifier[] {byStoredCount, byCommentCount}; // 보관순
    }
}
