package com.haejwo.tripcometrue.domain.city.service;

import com.haejwo.tripcometrue.domain.city.dto.ExchangeRateApiDto;
import com.haejwo.tripcometrue.domain.city.exception.ExchangeRateApiCallFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ExchangeRateApiCaller {

    private final RestTemplate restTemplate;

    @Value("${exchange-rate.api.url}")
    private String API_URL;

    @Value("${exchange-rate.api.key}")
    private String API_KEY;

    public List<ExchangeRateApiDto> call() {

        URI uri = buildUri(LocalDate.now());

        try {
            ResponseEntity<List<ExchangeRateApiDto>> responseEntity = restTemplate.exchange(
                uri, HttpMethod.GET, null, new ParameterizedTypeReference<>() {}
            );

            if(Objects.nonNull(responseEntity.getBody()) && responseEntity.getBody().get(0).result() != 1) {
                Integer result = responseEntity.getBody().get(0).result();
                String errorMessage = switch (result) {
                    case 2 -> "DATA 코드 오류";
                    case 3 -> "인증코드 오류";
                    case 4 -> "일일제한횟수 마감";
                    default -> "환율 API 호출 실패";
                };

                throw new ExchangeRateApiCallFailException(errorMessage);
            }

            return responseEntity.getBody();
        } catch (Exception e) {
            throw new ExchangeRateApiCallFailException();
        }
    }

    private URI buildUri(LocalDate date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        return UriComponentsBuilder
            .fromHttpUrl(API_URL)
            .queryParam("authkey", API_KEY)
            .queryParam("searchdate", dateFormatter.format(date))
            .queryParam("data", "AP01")
            .build()
            .encode()
            .toUri();
    }
}
