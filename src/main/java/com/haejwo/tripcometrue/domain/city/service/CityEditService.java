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
public class CityEditService {

    private final CityRepository cityRepository;

    @Transactional
    public CityResponseDto addCity(AddCityRequestDto request) {
        return CityResponseDto.fromEntity(
            cityRepository.save(request.toEntity())
        );
    }

    @Transactional
    public void deleteCity(Long id) {
        City city = getCityEntity(id);

        cityRepository.delete(city);
    }

    private City getCityEntity(Long id) {
        return cityRepository.findById(id)
            .orElseThrow(CityNotFoundException::new);
    }
}
