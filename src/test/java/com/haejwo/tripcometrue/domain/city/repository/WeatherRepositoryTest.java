package com.haejwo.tripcometrue.domain.city.repository;

import com.haejwo.tripcometrue.config.TestQuerydslConfig;
import com.haejwo.tripcometrue.domain.city.entity.City;
import com.haejwo.tripcometrue.global.enums.CurrencyUnit;
import com.haejwo.tripcometrue.domain.city.entity.Weather;
import com.haejwo.tripcometrue.global.enums.Country;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Import(TestQuerydslConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class WeatherRepositoryTest {

    @Autowired
    private CityRepository cityRepository;

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
    void findAllByCityAndMonthBetweenOrderByMonthAsc() {
        // given
        List<Integer> monthList = List.of(10, 11, 12, 1);

        // when
        List<Weather> result = weatherRepository.findAllByCityAndMonthInOrderByMonthAsc(city, monthList);

        // then
        assertThat(result).hasSize(4);
        assertThat(result.get(0).getMonth()).isEqualTo(1);
        assertThat(result.get(3).getMonth()).isEqualTo(12);
    }

    @AfterEach
    void cleanUp() {
        weatherRepository.deleteAll();
        cityRepository.deleteAll();
    }
}
