package com.haejwo.tripcometrue.domain.city.service;

import com.haejwo.tripcometrue.config.AbstractContainersSupport;
import com.haejwo.tripcometrue.domain.city.dto.response.CityInfoResponseDto;
import com.haejwo.tripcometrue.domain.city.dto.response.WeatherResponseDto;
import com.haejwo.tripcometrue.domain.city.entity.City;
import com.haejwo.tripcometrue.domain.city.entity.CurrencyUnit;
import com.haejwo.tripcometrue.domain.city.entity.Weather;
import com.haejwo.tripcometrue.domain.city.exception.CityNotFoundException;
import com.haejwo.tripcometrue.domain.city.repository.CityRepository;
import com.haejwo.tripcometrue.domain.city.repository.WeatherRepository;
import com.haejwo.tripcometrue.domain.place.entity.Place;
import com.haejwo.tripcometrue.domain.place.repositroy.PlaceRepository;
import com.haejwo.tripcometrue.global.enums.Country;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
class CityInfoReadServiceTest extends AbstractContainersSupport {

    @Autowired
    private CityInfoReadService cityInfoReadService;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private WeatherRepository weatherRepository;

    private City city;

    @BeforeEach
    void setUp() {
        city = cityRepository.save(
            City.builder()
                .name("방콕")
                .language("태국어")
                .timeDifference("2시간 느림")
                .currency(CurrencyUnit.THB)
                .visa("visa")
                .weatherRecommendation("11~12월이 여행하기 가장 좋은 시기입니다.")
                .weatherDescription("방콕 날씨 설명")
                .voltage("220V")
                .country(Country.THAILAND)
                .build()
        );

        for (int i = 1; i <= 15; i++) {
            placeRepository.save(
                Place.builder()
                    .city(city)
                    .address("여행지" + i + " 주소")
                    .name("방콕 여행지" + i)
                    .description("여행지 설명")
                    .storedCount(100 + i)
                    .build()
            );
        }

        for (int i = 1; i <= 12; i++) {
            weatherRepository.save(
                Weather.builder()
                    .city(city)
                    .month(i)
                    .maxAvgTemp("32.2")
                    .minAvgTemp("30.1")
                    .build()
            );
        }
    }

    @Test
    void getCityInfo() {
        // given
        long cityId = city.getId();

        // when
        CityInfoResponseDto cityInfo = cityInfoReadService.getCityInfo(cityId);

        // then
        assertThat(cityInfo.name()).isEqualTo("방콕");
        assertThat(cityInfo.language()).isEqualTo("태국어");
        assertThat(cityInfo.curUnit()).isEqualTo(CurrencyUnit.THB.name());
    }

    @Test
    void getWeatherInfo() {
        // given
        long cityId = city.getId();

        // when
        List<WeatherResponseDto> weatherInfos = cityInfoReadService.getWeatherInfo(cityId);

        // then
        assertThat(weatherInfos).hasSize(4);
    }

    @Test
    void getCityInfo_cityNotFoundException() {
        // given
        long cityId = 10000;

        // when & then
        assertThatThrownBy(() -> cityInfoReadService.getCityInfo(cityId))
            .isInstanceOf(CityNotFoundException.class);
    }

    @Test
    void getWeatherInfo_cityNotFoundException() {
        // given
        long cityId = 10000;

        // when & then
        assertThatThrownBy(() -> cityInfoReadService.getWeatherInfo(cityId))
            .isInstanceOf(CityNotFoundException.class);
    }

    @AfterEach
    void cleanUp() {
        log.info("cleanUp 실행");
        weatherRepository.deleteAll();
        placeRepository.deleteAll();
        cityRepository.deleteAll();
    }
}
