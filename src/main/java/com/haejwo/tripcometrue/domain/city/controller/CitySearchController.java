package com.haejwo.tripcometrue.domain.city.controller;

import com.haejwo.tripcometrue.domain.city.dto.response.CityResponseDto;
import com.haejwo.tripcometrue.domain.city.service.CitySearchService;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/v1/cities")
@RestController
public class CitySearchController {

    private final CitySearchService citySearchService;

    @GetMapping
    public ResponseEntity<ResponseDTO<List<CityResponseDto>>> search(
        @RequestParam("name") String name
    ) {
        return ResponseEntity
            .ok()
            .body(
                ResponseDTO.okWithData(
                    citySearchService.search(name)
                )
            );
    }
}
