package com.haejwo.tripcometrue.domain.place.repositroy;

import com.haejwo.tripcometrue.domain.city.entity.City;
import com.haejwo.tripcometrue.domain.city.entity.QCity;
import com.haejwo.tripcometrue.domain.place.entity.Place;
import com.haejwo.tripcometrue.domain.place.entity.QPlace;
import com.querydsl.core.BooleanBuilder;
import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

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
            .orderBy(place.storedCount.desc()) // TODO: 리뷰순 정렬 추가
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
            .orderBy(place.storedCount.desc(), place.createdAt.desc())
            .limit(size)
            .fetch();
    }
}
