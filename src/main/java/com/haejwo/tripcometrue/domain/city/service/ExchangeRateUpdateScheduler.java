package com.haejwo.tripcometrue.domain.city.service;

import com.haejwo.tripcometrue.domain.city.dto.api.ExchangeRateApiDto;
import com.haejwo.tripcometrue.domain.city.entity.CurrencyUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ExchangeRateUpdateScheduler {

    private final ExchangeRateApiCaller exchangeRateApiCaller;
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String EXCHANGE_RATE_REDIS_KEY_PREFIX = "exchange-rate:";

    // 매일 9:00, 12:00, 15:00 환율 정보 업데이트
    @Scheduled(cron = "0 0 9,12,15 * * *")
    public void update() {
        List<ExchangeRateApiDto> exchangeRates = exchangeRateApiCaller.call();

        for (ExchangeRateApiDto exchangeRate : exchangeRates) {
            String curUnitString = exchangeRate.curUnit().trim().substring(0, 3);

            if(curUnitString.equals("KRW")) continue;

            saveExchangeRateToRedis(exchangeRate, curUnitString);
        }
    }

    private void saveExchangeRateToRedis(ExchangeRateApiDto exchangeRate, String curUnitString) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        CurrencyUnit currencyUnit = CurrencyUnit.valueOf(curUnitString);

        String key = EXCHANGE_RATE_REDIS_KEY_PREFIX + currencyUnit;
        String value = currencyUnit.getStandard() + ":" + exchangeRate.dealBaseRate();

        if (currencyUnit.getStandard() != 1) {
            BigDecimal rate = new BigDecimal(exchangeRate.dealBaseRate())
                .divide(new BigDecimal(currencyUnit.getStandard()));

            value = "1:" + rate;
        }

        operations.set(key, value);
    }
}
