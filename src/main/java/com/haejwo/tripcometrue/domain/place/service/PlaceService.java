package com.haejwo.tripcometrue.domain.place.service;

import com.haejwo.tripcometrue.domain.place.dto.request.PlaceFilterRequestDto;
import com.haejwo.tripcometrue.domain.place.dto.request.PlaceRequestDto;
import com.haejwo.tripcometrue.domain.place.dto.response.PlaceResponseDto;
import com.haejwo.tripcometrue.domain.place.entity.Place;
import com.haejwo.tripcometrue.domain.place.exception.PlaceNotFoundException;
import com.haejwo.tripcometrue.domain.place.repositroy.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlaceService {

    private final PlaceRepository placeRepository;

    @Transactional
    public PlaceResponseDto addPlace(PlaceRequestDto requestDto) {

        Place requestPlace = requestDto.toEntity();
        Place savedPlace = placeRepository.save(requestPlace);
        PlaceResponseDto responseDto = PlaceResponseDto.fromEntity(savedPlace);

        return responseDto;

    }

    @Transactional(readOnly = true)
    public PlaceResponseDto findPlace(Long placeId) {

        Place findPlace = findPlaceById(placeId);

        PlaceResponseDto responseDto = PlaceResponseDto.fromEntity(findPlace);

        return responseDto;
    }

    @Transactional(readOnly = true)
    public Page<PlaceResponseDto> findPlaces(Pageable pageable, Integer storedCount) {

        Page<Place> findPlaces = placeRepository.findPlaceWithFilter(pageable, storedCount);

        Page<PlaceResponseDto> result = findPlaces.map(PlaceResponseDto::fromEntity);

        return result;

    }

    @Transactional
    public PlaceResponseDto modifyPlace(Long placeId, PlaceRequestDto requestDto) {

        Place place = findPlaceById(placeId);
        place.update(requestDto);
        PlaceResponseDto responseDto = PlaceResponseDto.fromEntity(place);

        return responseDto;
    }

    @Transactional
    public void removePlace(Long placeId) {
        Place findPlace = findPlaceById(placeId);
        placeRepository.delete(findPlace);
    }



    private Place findPlaceById(Long placeId) {

        Place findPlace = placeRepository.findById(placeId)
            .orElseThrow(PlaceNotFoundException::new);

        return findPlace;
    }



}
