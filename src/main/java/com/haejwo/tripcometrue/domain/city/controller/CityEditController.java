package com.haejwo.tripcometrue.domain.city.controller;

import com.haejwo.tripcometrue.domain.city.dto.request.AddCityRequestDto;
import com.haejwo.tripcometrue.domain.city.dto.response.CityResponseDto;
import com.haejwo.tripcometrue.domain.city.service.CityEditService;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/v1/cities")
@RestController
public class CityEditController {

    private final CityEditService cityEditService;

    @PostMapping
    public ResponseEntity<ResponseDTO<CityResponseDto>> addCity(
        @RequestBody AddCityRequestDto request
    ) {

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                ResponseDTO.okWithData(cityEditService.addCity(request))
            );
    }

    @DeleteMapping("/{cityId}")
    public ResponseEntity<ResponseDTO<Void>> deleteCity(
        @PathVariable("cityId") Long cityId
    ) {

        cityEditService.deleteCity(cityId);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ResponseDTO.ok());
    }
}
