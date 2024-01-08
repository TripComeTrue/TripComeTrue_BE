package com.haejwo.tripcometrue.domain.city.service;

import com.haejwo.tripcometrue.domain.city.dto.request.AddCityRequestDto;
import com.haejwo.tripcometrue.domain.city.dto.response.CityResponseDto;
import com.haejwo.tripcometrue.domain.city.entity.City;
import com.haejwo.tripcometrue.domain.city.exception.CityNotFoundException;
import com.haejwo.tripcometrue.domain.city.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CityService {

    private final CityRepository cityRepository;

    @Transactional
    public CityResponseDto addCity(AddCityRequestDto request) {
        return CityResponseDto.fromEntity(
            cityRepository.save(request.toEntity())
        );
    }

    @Transactional(readOnly = true)
    public CityResponseDto findCity(Long id) {
        return CityResponseDto.fromEntity(
            cityRepository.findById(id)
                .orElseThrow(CityNotFoundException::new)
        );
    }

    @Transactional
    public void deleteCity(Long id) {
        City city = cityRepository.findById(id)
            .orElseThrow(CityNotFoundException::new);

        cityRepository.delete(city);
    }
}
