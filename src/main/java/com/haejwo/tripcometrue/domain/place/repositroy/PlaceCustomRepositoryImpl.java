package com.haejwo.tripcometrue.domain.place.repositroy;

import com.haejwo.tripcometrue.domain.place.dto.request.PlaceFilterRequestDto;
import com.haejwo.tripcometrue.domain.place.entity.Place;
import com.haejwo.tripcometrue.domain.place.entity.QPlace;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class PlaceCustomRepositoryImpl extends QuerydslRepositorySupport implements PlaceCustomRepository {

    public PlaceCustomRepositoryImpl() {
        super(Place.class);
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
}
