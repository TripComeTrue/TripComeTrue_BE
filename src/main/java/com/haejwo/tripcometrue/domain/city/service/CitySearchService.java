package com.haejwo.tripcometrue.domain.city.service;

import com.haejwo.tripcometrue.domain.city.dto.response.CityResponseDto;
import com.haejwo.tripcometrue.domain.city.entity.City;
import com.haejwo.tripcometrue.global.enums.CurrencyUnit;
import com.haejwo.tripcometrue.domain.city.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CitySearchService {

    private final CityRepository cityRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String EXCHANGE_RATE_REDIS_KEY_PREFIX = "exchange-rate:";

    @Transactional(readOnly = true)
    public List<CityResponseDto> search(String name) {
        return cityRepository
            .findBySearchParams(name)
            .stream()
            .map(city -> {
                    String exchangeRate = getExchangeRate(city);
                    return CityResponseDto.fromEntity(city, exchangeRate);
                }
            )
            .toList();
    }

    private String getExchangeRate(City city) {
        CurrencyUnit currency = city.getCurrency();
        if (Objects.isNull(currency)) {
            return null;
        }

        return (String) redisTemplate.opsForValue()
            .get(EXCHANGE_RATE_REDIS_KEY_PREFIX + currency.name());
    }
}
