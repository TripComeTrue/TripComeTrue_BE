package com.haejwo.tripcometrue.domain.city.controller;

import com.haejwo.tripcometrue.domain.city.dto.request.AddCityRequestDto;
import com.haejwo.tripcometrue.domain.city.dto.response.CityInfoResponseDto;
import com.haejwo.tripcometrue.domain.city.dto.response.CityResponseDto;
import com.haejwo.tripcometrue.domain.city.dto.response.ExchangeRateResponseDto;
import com.haejwo.tripcometrue.domain.city.dto.response.WeatherResponseDto;
import com.haejwo.tripcometrue.domain.city.service.CityService;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/v1/cities")
@RestController
public class CityController {

    private final CityService cityService;

    @PostMapping
    public ResponseEntity<ResponseDTO<CityResponseDto>> addCity(
        @RequestBody AddCityRequestDto request
    ) {

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                ResponseDTO.okWithData(cityService.addCity(request))
            );
    }

    @GetMapping("/{cityId}")
    public ResponseEntity<ResponseDTO<CityInfoResponseDto>> getCityInfo(
        @PathVariable("cityId") Long cityId
    ) {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                ResponseDTO.okWithData(cityService.getCityInfo(cityId))
            );
    }

    @GetMapping("/exchange-rates")
    public ResponseEntity<ResponseDTO<ExchangeRateResponseDto>> getExchangeRate(
        @RequestParam(name = "curUnit") String curUnit
    ) {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                ResponseDTO.okWithData(cityService.getExchangeRate(curUnit))
            );
    }

    @GetMapping("/{cityId}/weathers")
    public ResponseEntity<ResponseDTO<List<WeatherResponseDto>>> getWeatherInfo(
        @PathVariable("cityId") Long cityId
    ) {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                ResponseDTO.okWithData(cityService.getWeatherInfo(cityId))
            );
    }

    @GetMapping("/v1/cities/{cityId}/hot-places")
    public ResponseEntity<ResponseDTO<?>> getHotPlaces(
        @PathVariable("cityId") Long cityId
    ) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                ResponseDTO.okWithData(cityService.getHotPlaces(cityId))
            );
    }

    @DeleteMapping("/{cityId}")
    public ResponseEntity<ResponseDTO<Void>> deleteCity(
        @PathVariable("cityId") Long cityId
    ) {

        cityService.deleteCity(cityId);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ResponseDTO.ok());
    }
}
