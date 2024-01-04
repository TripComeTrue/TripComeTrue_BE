package com.haejwo.tripcometrue.domain.place.repositroy;

import com.haejwo.tripcometrue.domain.place.dto.request.PlaceFilterRequestDto;
import com.haejwo.tripcometrue.domain.place.entity.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlaceCustomRepository {

    Page<Place> findPlaceWithFilter(Pageable pageable,
                                    Integer storedCount);

}
