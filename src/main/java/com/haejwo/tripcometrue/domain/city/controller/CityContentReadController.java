package com.haejwo.tripcometrue.domain.city.controller;

import com.haejwo.tripcometrue.domain.city.dto.response.*;
import com.haejwo.tripcometrue.domain.city.service.CityContentReadService;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import com.haejwo.tripcometrue.global.validator.annotation.HomeTopListQueryType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/v1/cities")
@RestController
public class CityContentReadController {

    private final CityContentReadService cityContentReadService;

    // 홈피드 TOP 인기 도시 리스트 조회
    @GetMapping("/top-list")
    public ResponseEntity<ResponseDTO<List<TopCityResponseDto>>> getTopCityList(
        @RequestParam @HomeTopListQueryType String type
    ) {
        return ResponseEntity
            .ok()
            .body(
                ResponseDTO.okWithData(
                    cityContentReadService.getTopCityList(type)
                )
            );
    }

    // 도시 관련 여행 후기 조회
    @GetMapping("/{cityId}/trip-records")
    public ResponseEntity<ResponseDTO<?>> getTripRecordsByCityId(
        @PathVariable("cityId") Long cityId,
        @RequestParam("totalDays") String totalDays
    ) {
        // TODO: 도시 여행 후기 리스트 조회 서비스 로직 구현
        return ResponseEntity
            .ok()
            .body(
                ResponseDTO.okWithData(null)
            );
    }

    // 도시 갤러리 더보기 조회
    @GetMapping("/{cityId}/images")
    public ResponseEntity<ResponseDTO<SliceResponse<CityImageContentResponseDto>>> getImagesByCityIdPagination(
        @PathVariable("cityId") Long cityId,
        @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
        ) {
        return ResponseEntity
            .ok()
            .body(
                ResponseDTO.okWithData(
                    cityContentReadService.getImages(cityId, pageable)
                )
            );
    }

    // 도시 갤러리 리스트 조회
    @GetMapping("/{cityId}/images/list")
    public ResponseEntity<ResponseDTO<List<CityImageContentResponseDto>>> getImagesByCityId(
        @PathVariable("cityId") Long cityId
    ) {
        return ResponseEntity
            .ok()
            .body(
                ResponseDTO.okWithData(
                    cityContentReadService.getImages(cityId)
                )
            );
    }

    // 도시 쇼츠 더보기 조회
    @GetMapping("/{cityId}/videos")
    public ResponseEntity<ResponseDTO<SliceResponse<CityVideoContentResponseDto>>> getVideosByCityIdPagination(
        @PathVariable("cityId") Long cityId,
        @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity
            .ok()
            .body(
                ResponseDTO.okWithData(
                    cityContentReadService.getVideos(cityId, pageable)
                )
            );
    }

    // 도시 쇼츠 리스트 조회
    @GetMapping("/{cityId}/videos/list")
    public ResponseEntity<ResponseDTO<List<CityVideoContentResponseDto>>> getVideosByCityId(
        @PathVariable("cityId") Long cityId
    ) {
        return ResponseEntity
            .ok()
            .body(
                ResponseDTO.okWithData(
                    cityContentReadService.getVideos(cityId)
                )
            );
    }

    // 도시 핫플레이스 조회
    @GetMapping("/{cityId}/hot-places")
    public ResponseEntity<ResponseDTO<List<CityPlaceResponseDto>>> getHotPlaces(
        @PathVariable("cityId") Long cityId
    ) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                ResponseDTO.okWithData(cityContentReadService.getHotPlaces(cityId))
            );
    }

    // 도시 여행지 더보기 조회
    @GetMapping("/{cityId}/places")
    public ResponseEntity<ResponseDTO<SliceResponse<CityPlaceResponseDto>>> getPlacesByCityIdPagination(
        @PathVariable("cityId") Long cityId,
        @PageableDefault(sort = "storedCount", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                ResponseDTO.okWithData(cityContentReadService.getPlaces(cityId, pageable))
            );
    }

    // 위/경도 정보 포함 도시 여행지 리스트 조회
    @GetMapping("/{cityId}/places/list")
    public ResponseEntity<ResponseDTO<List<CityPlaceWithLatLongResponseDto>>> getPlacesWithLatLongByCityId(
        @PathVariable("cityId") Long cityId
    ) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                ResponseDTO.okWithData(cityContentReadService.getPlacesWithLatLong(cityId))
            );
    }
}
