package com.haejwo.tripcometrue.domain.city.repository;

import com.haejwo.tripcometrue.domain.city.entity.City;
import com.haejwo.tripcometrue.global.enums.Country;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.haejwo.tripcometrue.domain.city.entity.QCity.city;

@RequiredArgsConstructor
public class CityRepositoryImpl implements CityRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<City> findTopCityListDomestic(int size) {
        return queryFactory
            .selectFrom(city)
            .where(city.country.eq(Country.KOREA))
            .orderBy(city.storeCount.desc())
            .limit(size)
            .fetch();
    }

    @Override
    public List<City> findTopCityListOverseas(int size) {
        return queryFactory
            .selectFrom(city)
            .where(city.country.eq(Country.KOREA).not())
            .orderBy(city.storeCount.desc())
            .limit(size)
            .fetch();
    }
}
