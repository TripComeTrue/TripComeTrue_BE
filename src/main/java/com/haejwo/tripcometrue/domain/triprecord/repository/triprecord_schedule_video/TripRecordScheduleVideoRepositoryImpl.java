package com.haejwo.tripcometrue.domain.triprecord.repository.triprecord_schedule_video;

import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordScheduleVideo;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

import static com.haejwo.tripcometrue.domain.place.entity.QPlace.place;
import static com.haejwo.tripcometrue.domain.triprecord.entity.QTripRecordSchedule.tripRecordSchedule;
import static com.haejwo.tripcometrue.domain.triprecord.entity.QTripRecordScheduleVideo.tripRecordScheduleVideo;

@RequiredArgsConstructor
public class TripRecordScheduleVideoRepositoryImpl implements TripRecordScheduleVideoRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<TripRecordScheduleVideo> findByCityId(Long cityId, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        List<TripRecordScheduleVideo> content = queryFactory
            .selectFrom(tripRecordScheduleVideo)
            .leftJoin(tripRecordScheduleVideo.tripRecordSchedule, tripRecordSchedule).fetchJoin()
            .leftJoin(tripRecordSchedule.place, place)
            .where(
                place.city.id.eq(cityId)
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
    public List<TripRecordScheduleVideo> findByCityIdOrderByCreatedAtDescLimitSize(Long cityId, Integer size) {
        return queryFactory
            .selectFrom(tripRecordScheduleVideo)
            .join(tripRecordScheduleVideo.tripRecordSchedule, tripRecordSchedule).fetchJoin()
            .join(tripRecordSchedule.place, place)
            .where(
                place.city.id.eq(cityId)
            )
            .orderBy(tripRecordScheduleVideo.createdAt.desc())
            .limit(size)
            .fetch();
    }

    private OrderSpecifier<?>[] getSort(Pageable pageable) {
        OrderSpecifier<LocalDateTime> newest = new OrderSpecifier<>(Order.DESC, tripRecordScheduleVideo.createdAt);
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
                        return new OrderSpecifier[] {new OrderSpecifier<>(direction, tripRecordScheduleVideo.id)};
                    case "createdAt":
                        return new OrderSpecifier[] {new OrderSpecifier<>(direction, tripRecordScheduleVideo.createdAt)};
                    case "storedCount":
                        return new OrderSpecifier[] {new OrderSpecifier<>(direction, tripRecordSchedule.tripRecord.storeCount), newest};
                }
            }
        }

        return new OrderSpecifier[] {newest}; // 최신순
    }
}
