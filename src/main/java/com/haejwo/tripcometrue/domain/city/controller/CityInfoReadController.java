package com.haejwo.tripcometrue.domain.city.controller;

import com.haejwo.tripcometrue.domain.city.dto.response.CityInfoResponseDto;
import com.haejwo.tripcometrue.domain.city.dto.response.ExchangeRateResponseDto;
import com.haejwo.tripcometrue.domain.city.dto.response.WeatherResponseDto;
import com.haejwo.tripcometrue.domain.city.service.CityInfoReadService;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/v1/cities")
@RestController
public class CityInfoReadController {

    private final CityInfoReadService cityInfoReadService;

    @GetMapping("/{cityId}")
    public ResponseEntity<ResponseDTO<CityInfoResponseDto>> getCityInfo(
        @PathVariable("cityId") Long cityId
    ) {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                ResponseDTO.okWithData(cityInfoReadService.getCityInfo(cityId))
            );
    }

    @GetMapping("/exchange-rates")
    public ResponseEntity<ResponseDTO<ExchangeRateResponseDto>> getExchangeRate(
        @RequestParam(name = "curUnit") String curUnit
    ) {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                ResponseDTO.okWithData(cityInfoReadService.getExchangeRate(curUnit))
            );
    }

    @GetMapping("/{cityId}/weathers")
    public ResponseEntity<ResponseDTO<List<WeatherResponseDto>>> getWeatherInfo(
        @PathVariable("cityId") Long cityId
    ) {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                ResponseDTO.okWithData(cityInfoReadService.getWeatherInfo(cityId))
            );
    }
}
