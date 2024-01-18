package com.haejwo.tripcometrue.domain.place.repositroy;

import com.haejwo.tripcometrue.domain.city.entity.City;
import com.haejwo.tripcometrue.domain.city.entity.QCity;
import com.haejwo.tripcometrue.domain.place.dto.response.PlaceMapInfoResponseDto;
import com.haejwo.tripcometrue.domain.place.entity.Place;
import com.haejwo.tripcometrue.domain.place.entity.QPlace;
import com.haejwo.tripcometrue.domain.triprecord.entity.QTripRecordSchedule;
import com.haejwo.tripcometrue.domain.triprecord.entity.QTripRecordScheduleImage;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
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
            .orderBy(place.storedCount.desc(), place.createdAt.desc())
            .limit(size)
            .fetch();
    }

    @Override
    public List<PlaceMapInfoResponseDto> findPlaceMapInfoListByPlaceId(Long placeId) {

        QPlace qPlace = QPlace.place;
        QCity qCity = QCity.city;
        QTripRecordSchedule qTripRecordSchedule = QTripRecordSchedule.tripRecordSchedule;
        QTripRecordScheduleImage qTripRecordScheduleImage = QTripRecordScheduleImage.tripRecordScheduleImage;

        List<Place> places = queryFactory
            .selectFrom(qPlace)
            .join(qPlace.city, qCity)
            .where(qCity.id.eq(
                queryFactory
                    .select(qCity.id)
                    .from(qPlace)
                    .where(qPlace.id.eq(placeId))
                    .fetchOne()
            ))
            .fetch();

        List<Long> placeIds = places.stream().map(Place::getId).collect(Collectors.toList());

        List<Tuple> images = queryFactory
            .select(qTripRecordSchedule.place.id, qTripRecordScheduleImage.imageUrl.min())
            .from(qTripRecordSchedule)
            .join(qTripRecordSchedule.tripRecordScheduleImages, qTripRecordScheduleImage)
            .where(qTripRecordSchedule.place.id.in(placeIds))
            .groupBy(qTripRecordSchedule.place.id)
            .fetch();

        List<PlaceMapInfoResponseDto> result = placeIds.stream()
            .map(id -> { // placeId를 id로 변경
                String imageUrl = images.stream()
                    .filter(tuple -> tuple.get(0, Long.class).equals(id))
                    .map(tuple -> tuple.get(1, String.class))
                    .findFirst()
                    .orElse(null);
                Place place = places.stream()
                    .filter(p -> p.getId().equals(id))
                    .findFirst()
                    .orElseThrow();
                return PlaceMapInfoResponseDto.builder()
                    .placeId(place.getId())
                    .placeName(place.getName())
                    .latitude(place.getLatitude())
                    .longitude(place.getLongitude())
                    .storeCount(place.getStoredCount())
                    .commentCount(place.getCommentCount())
                    .imageUrl(imageUrl)
                    .build();
            })
            .collect(Collectors.toList());

        return result;

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
