package com.haejwo.tripcometrue.domain.place.repositroy;

import com.haejwo.tripcometrue.domain.city.entity.City;
import com.haejwo.tripcometrue.domain.city.entity.QCity;
import com.haejwo.tripcometrue.domain.place.dto.response.PlaceMapInfoResponseDto;
import com.haejwo.tripcometrue.domain.place.dto.response.PlaceNearbyResponseDto;
import com.haejwo.tripcometrue.domain.place.entity.Place;
import com.haejwo.tripcometrue.domain.place.entity.QPlace;
import com.haejwo.tripcometrue.domain.triprecord.entity.QTripRecordSchedule;
import com.haejwo.tripcometrue.domain.triprecord.entity.QTripRecordScheduleImage;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.NullExpression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.OrderSpecifier.NullHandling;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import static com.haejwo.tripcometrue.domain.city.entity.QCity.city;
import static com.haejwo.tripcometrue.domain.place.entity.QPlace.place;

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

        if (storedCount != null && storedCount >= 0) {
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
    public Slice<Place> findPlacesByCityIdAndPlaceName(Long cityId, String placeName, Pageable pageable) {

        int pageSize = pageable.getPageSize();
        List<Place> content = queryFactory
            .selectFrom(place)
            .join(place.city, city)
            .where(
                city.id.eq(cityId),
                containsIgnoreCasePlaceName(placeName)
            )
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
    public Slice<Place> findPlacesWithCityByPlaceName(String placeName, Pageable pageable) {

        int pageSize = pageable.getPageSize();
        List<Place> content = queryFactory
            .selectFrom(place)
            .join(place.city, city).fetchJoin()
            .where(
                containsIgnoreCasePlaceName(placeName)
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
    public List<Place> findPlacesByCityAndOrderByStoredCountLimitSize(City city, int size) {

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

        QPlace qPlace = place;
        QCity qCity = city;
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

    @Override
    public List<PlaceNearbyResponseDto> findNearbyPlaces(Long placeId) {

        QPlace qPlace = QPlace.place;
        QTripRecordSchedule qTripRecordSchedule = QTripRecordSchedule.tripRecordSchedule;
        QTripRecordScheduleImage qTripRecordScheduleImage = QTripRecordScheduleImage.tripRecordScheduleImage;

        // 주어진 placeId에 해당하는 장소의 위도, 경도를 찾는다.
        Place centerPlace = queryFactory
            .selectFrom(qPlace)
            .where(qPlace.id.eq(placeId))
            .fetchOne();

        // 주변 장소를 뽑아냅니다. (1도 범위 내면 보통 11km 안쪽임)
        List<Place> nearbyPlaces = queryFactory
            .selectFrom(qPlace)
            .where(qPlace.latitude.between(centerPlace.getLatitude() - 1, centerPlace.getLatitude() + 1)
                .and(qPlace.longitude.between(centerPlace.getLongitude() - 1, centerPlace.getLongitude() + 1)))
            .orderBy(qPlace.storedCount.desc())
            .where(qPlace.id.ne(placeId))
            .limit(5)
            .fetch();

        // 각 장소에 해당하는 스케줄 이미지를 찾아 PlaceNearbyResponseDto 객체를 생성한다. (어차피 컨텐츠가 5개 고정이여서 이게 더 간단함)
        List<PlaceNearbyResponseDto> result = nearbyPlaces.stream()
                    .map(place -> {
                        String imageUrl = queryFactory
                            .select(qTripRecordScheduleImage.imageUrl)
                            .from(qTripRecordScheduleImage)
                            .join(qTripRecordScheduleImage.tripRecordSchedule, qTripRecordSchedule)
                            .where(qTripRecordSchedule.place.id.eq(place.getId()))
                            .orderBy(qTripRecordScheduleImage.id.asc())
                            .fetchFirst();

                        return new PlaceNearbyResponseDto(
                            place.getId(),
                            place.getName(),
                            imageUrl,
                            place.getLatitude(),
                            place.getLongitude(),
                            place.getStoredCount(),
                            place.getReviewCount(),
                            place.getCommentCount()
                        );
                    })
                    .collect(Collectors.toList());

        return result;
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

    private OrderSpecifier<?>[] getSort(Pageable pageable) {

        List<OrderSpecifier<?>> orderSpecifiers = new LinkedList<>();
        if (!pageable.getSort().isEmpty()) {
            for (Sort.Order sortOrder : pageable.getSort()) {
                Order direction = sortOrder.getDirection().isAscending() ? Order.ASC : Order.DESC;

                String property = sortOrder.getProperty();
                switch (property) {
                    case "id":
                        orderSpecifiers.add(new OrderSpecifier<>(direction, place.id));
                        break;
                    case "createdAt":
                        orderSpecifiers.add(new OrderSpecifier<>(direction, place.createdAt));
                        break;
                    case "storedCount":
                        orderSpecifiers.add(new OrderSpecifier<>(direction, place.storedCount));
                        break;
                    case "commentCount":
                        orderSpecifiers.add(new OrderSpecifier<>(direction, place.commentCount));
                        break;

                    // TODO: 정렬 기준 예외 처리
                }
            }
        }

        if(orderSpecifiers.isEmpty()) {
            orderSpecifiers.add(new OrderSpecifier(Order.ASC, NullExpression.DEFAULT, NullHandling.Default));
        }

        return orderSpecifiers.toArray(OrderSpecifier[]::new);
    }
}
