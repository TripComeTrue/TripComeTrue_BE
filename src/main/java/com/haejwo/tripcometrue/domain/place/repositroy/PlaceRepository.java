package com.haejwo.tripcometrue.domain.place.repositroy;

import com.haejwo.tripcometrue.domain.place.entity.Place;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends
    JpaRepository<Place, Long>, PlaceRepositoryCustom
{
    List<Place> findByCityId(Long cityId);
}
