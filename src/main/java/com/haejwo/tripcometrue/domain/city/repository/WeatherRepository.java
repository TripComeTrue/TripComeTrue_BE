package com.haejwo.tripcometrue.domain.city.repository;

import com.haejwo.tripcometrue.domain.city.entity.City;
import com.haejwo.tripcometrue.domain.city.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeatherRepository extends JpaRepository<Weather, Long> {

    List<Weather> findAllByCityAndMonthInOrderByMonthAsc(City city, List<Integer> months);
}
