package com.haejwo.tripcometrue.domain.city.repository;

import com.haejwo.tripcometrue.domain.city.entity.City;

import java.util.List;

public interface CityRepositoryCustom {

    List<City> findTopCityListDomestic(int size);

    List<City> findTopCityListOverseas(int size);
}
