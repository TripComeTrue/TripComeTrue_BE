package com.haejwo.tripcometrue.domain.place.repositroy;

import com.haejwo.tripcometrue.domain.city.entity.City;
import com.haejwo.tripcometrue.domain.place.dto.response.PlaceMapInfoResponseDto;
import com.haejwo.tripcometrue.domain.place.dto.response.PlaceNearbyResponseDto;
import com.haejwo.tripcometrue.domain.place.entity.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface PlaceCustomRepository {

    Page<Place> findPlaceWithFilter(Pageable pageable,
                                    Integer storedCount);

    Slice<Place> findPlacesByCityId(Long cityId, Pageable pageable);

    List<Place> findPlacesByCityAndOrderByStoredCountLimitSize(City city, int size);

    List<PlaceMapInfoResponseDto> findPlaceMapInfoListByPlaceId(Long placeId);

    List<PlaceNearbyResponseDto> findNearbyPlaces(Long placeId);
}
