package com.haejwo.tripcometrue.domain.city.repository;

import com.haejwo.tripcometrue.domain.city.entity.City;
import com.haejwo.tripcometrue.global.enums.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository
    extends JpaRepository<City, Long>, CityRepositoryCustom {

    Optional<City> findByNameAndCountry(String name, Country country);
}
