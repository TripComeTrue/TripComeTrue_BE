package com.haejwo.tripcometrue.domain.place.service;

import com.haejwo.tripcometrue.domain.place.dto.request.PlaceRequestDTO;
import com.haejwo.tripcometrue.domain.place.dto.response.PlaceResponseDTO;
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
    public PlaceResponseDTO addPlace(PlaceRequestDTO requestDto) {

        System.out.println(requestDto.storedCount());
        Place requestPlace = requestDto.toEntity();
        Place savedPlace = placeRepository.save(requestPlace);
        System.out.println(savedPlace.getStoredCount());
        PlaceResponseDTO responseDto = PlaceResponseDTO.fromEntity(savedPlace);

        return responseDto;

    }

    @Transactional(readOnly = true)
    public PlaceResponseDTO findPlace(Long placeId) {

        Place findPlace = findPlaceById(placeId);

        PlaceResponseDTO responseDto = PlaceResponseDTO.fromEntity(findPlace);

        return responseDto;
    }

    @Transactional(readOnly = true)
    public Page<PlaceResponseDTO> findPlaces(Pageable pageable, Integer storedCount) {

        Page<Place> findPlaces = placeRepository.findPlaceWithFilter(pageable, storedCount);

        Page<PlaceResponseDTO> result = findPlaces.map(PlaceResponseDTO::fromEntity);

        return result;

    }

    @Transactional
    public PlaceResponseDTO modifyPlace(Long placeId, PlaceRequestDTO requestDto) {

        Place place = findPlaceById(placeId);
        place.update(requestDto);
        PlaceResponseDTO responseDto = PlaceResponseDTO.fromEntity(place);

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
