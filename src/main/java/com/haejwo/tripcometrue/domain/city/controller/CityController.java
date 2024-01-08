package com.haejwo.tripcometrue.domain.city.controller;

import com.haejwo.tripcometrue.domain.city.dto.request.AddCityRequestDto;
import com.haejwo.tripcometrue.domain.city.dto.response.CityResponseDto;
import com.haejwo.tripcometrue.domain.city.service.CityService;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<CityResponseDto>> findCity(
        @PathVariable("id") Long id
    ) {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                ResponseDTO.okWithData(cityService.findCity(id))
            );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteCity(
        @PathVariable("id") Long id
    ) {

        cityService.deleteCity(id);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ResponseDTO.ok());
    }
}
