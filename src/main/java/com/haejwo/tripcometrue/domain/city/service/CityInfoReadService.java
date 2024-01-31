package com.haejwo.tripcometrue.domain.city.service;

import com.haejwo.tripcometrue.domain.city.dto.response.CityInfoResponseDto;
import com.haejwo.tripcometrue.domain.city.dto.response.ExchangeRateResponseDto;
import com.haejwo.tripcometrue.domain.city.dto.response.WeatherResponseDto;
import com.haejwo.tripcometrue.domain.city.entity.City;
import com.haejwo.tripcometrue.global.enums.CurrencyUnit;
import com.haejwo.tripcometrue.domain.city.entity.Weather;
import com.haejwo.tripcometrue.domain.city.exception.CityNotFoundException;
import com.haejwo.tripcometrue.domain.city.repository.CityRepository;
import com.haejwo.tripcometrue.domain.city.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CityInfoReadService {

    private final CityRepository cityRepository;
    private final WeatherRepository weatherRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    private static final int WEATHER_MONTH_GAP = 3;

    @Transactional(readOnly = true)
    public CityInfoResponseDto getCityInfo(Long cityId) {
        return CityInfoResponseDto.fromEntity(
            cityRepository.findById(cityId)
                .orElseThrow(CityNotFoundException::new)
        );
    }

    @Transactional(readOnly = true)
    public ExchangeRateResponseDto getExchangeRateByCityId(Long cityId) {

        City city = cityRepository.findById(cityId)
            .orElseThrow(CityNotFoundException::new);

        CurrencyUnit currencyUnit = city.getCurrency();
        String exchangeRate = (String) redisTemplate.opsForValue()
            .get("exchange-rate:" + currencyUnit);

        return ExchangeRateResponseDto.builder()
            .curUnit(currencyUnit.name())
            .curName(currencyUnit.getCurrencyName())
            .exchangeRate(exchangeRate)
            .country(city.getCountry().getDescription())
            .build();
    }

    @Transactional(readOnly = true)
    public List<WeatherResponseDto> getWeatherInfo(Long cityId) {
        City city = cityRepository.findById(cityId)
            .orElseThrow(CityNotFoundException::new);

        // 현재 달 포함 향후 3개월
        int curMonth = LocalDate.now().getMonthValue();
        List<Integer> months = new ArrayList<>();
        for (int i = 0; i <= WEATHER_MONTH_GAP; i++) {
            months.add(((curMonth + i) % 13) + 1);
        }

        List<Weather> weathers = weatherRepository.findAllByCityAndMonthInOrderByMonthAsc(city, months);

        return sortWeatherInfos(weathers)
            .stream()
            .map(
                w -> WeatherResponseDto.fromEntity(w, convertToTempF(w.getMaxAvgTemp()), convertToTempF(w.getMinAvgTemp()))
            ).toList();
    }

    private List<Weather> sortWeatherInfos(List<Weather> weathers) {
        int lastIdx = weathers.size() - 1;

        if(weathers.get(lastIdx).getMonth() - weathers.get(0).getMonth() > WEATHER_MONTH_GAP) {
            weathers.add(0, weathers.remove(lastIdx));

            for (int i = lastIdx; i >= 0; i--) {
                int last = weathers.get(lastIdx).getMonth();
                int prev = weathers.get(lastIdx - 1).getMonth();

                weathers.add(0, weathers.remove(lastIdx));

                if (last - prev != 1) {
                    break;
                }
            }
        }

        return weathers;
    }

    private String convertToTempF(String tempC) {
        return new BigDecimal(tempC)
            .multiply(new BigDecimal("1.8"))
            .add(new BigDecimal("32"))
            .setScale(2, RoundingMode.HALF_UP)
            .toString();
    }
}
