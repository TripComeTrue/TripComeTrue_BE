package com.haejwo.tripcometrue.domain.city.service;

import com.haejwo.tripcometrue.domain.city.dto.response.*;
import com.haejwo.tripcometrue.domain.city.entity.City;
import com.haejwo.tripcometrue.domain.city.exception.CityNotFoundException;
import com.haejwo.tripcometrue.domain.city.repository.CityRepository;
import com.haejwo.tripcometrue.domain.place.entity.Place;
import com.haejwo.tripcometrue.domain.place.repositroy.PlaceRepository;
import com.haejwo.tripcometrue.domain.triprecord.dto.query.TripRecordScheduleImageWithPlaceIdQueryDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord_schedule_media.TripRecordScheduleVideoListItemResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.repository.triprecord_schedule_image.TripRecordScheduleImageRepository;
import com.haejwo.tripcometrue.domain.triprecord.repository.triprecord_schedule_video.TripRecordScheduleVideoRepository;
import com.haejwo.tripcometrue.global.util.SliceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CityContentReadService {

    private final CityRepository cityRepository;
    private final PlaceRepository placeRepository;
    private final TripRecordScheduleImageRepository tripRecordScheduleImageRepository;
    private final TripRecordScheduleVideoRepository tripRecordScheduleVideoRepository;

    private static final int CITY_HOT_PLACES_SIZE = 10;
    private static final int CITY_MEDIA_CONTENT_SIZE = 10;
    private static final int HOME_CONTENT_SIZE = 5;

    // 홈피드 인기 도시 리스트 조회
    public List<TopCityResponseDto> getTopCityList(String type) {
        if (type.equalsIgnoreCase("domestic")) {
            return cityRepository
                .findTopCityListDomestic(HOME_CONTENT_SIZE)
                .stream()
                .map(TopCityResponseDto::fromEntity)
                .toList();
        } else {
            return cityRepository
                .findTopCityListOverseas(HOME_CONTENT_SIZE)
                .stream()
                .map(TopCityResponseDto::fromEntity)
                .toList();
        }
    }

    // 도시 핫플레이스 리스트 조회
    @Transactional(readOnly = true)
    public List<CityPlaceResponseDto> getHotPlaces(Long id) {
        City city = getCityEntity(id);

        List<Place> places = placeRepository
            .findPlacesByCityAndOrderByStoredCountLimitSize(city, CITY_HOT_PLACES_SIZE);

        Map<Long, List<TripRecordScheduleImageWithPlaceIdQueryDto>> imageMap = getImageMapFromPlaceIds(places);

        return places
            .stream()
            .map(place -> {
                String imageUrl = Objects.isNull(imageMap.get(place.getId())) ? null :
                    imageMap.get(place.getId())
                        .stream()
                        .filter(Objects::nonNull)
                        .findFirst()
                        .map(TripRecordScheduleImageWithPlaceIdQueryDto::imageUrl)
                        .orElse(null);

                return CityPlaceResponseDto.fromEntity(place, imageUrl);
            })
            .toList();
    }

    // 위/경도 정보 포함 도시 여행지 전체 조회
    @Transactional(readOnly = true)
    public List<CityPlaceWithLatLongResponseDto> getPlacesWithLatLong(Long cityId) {
        List<Place> places = placeRepository.findByCityId(cityId);

        Map<Long, List<TripRecordScheduleImageWithPlaceIdQueryDto>> imageMap = getImageMapFromPlaceIds(places);

        return places
            .stream()
            .map(place -> {
                String imageUrl = imageMap.get(place.getId()).get(0).imageUrl();
                return CityPlaceWithLatLongResponseDto.fromEntity(place, imageUrl);
            })
            .toList();
    }

    // 도시 여행지 전체 조회 및 검색 (스크롤 페이징)
    @Transactional(readOnly = true)
    public SliceResponseDto<CityPlaceResponseDto> getPlaces(Long cityId, String placeName, Pageable pageable) {
        Slice<Place> places = placeRepository.findPlacesByCityIdAndPlaceName(cityId, placeName, pageable);

        Map<Long, List<TripRecordScheduleImageWithPlaceIdQueryDto>> imageMap = getImageMapFromPlaceIds(places.getContent());

        return SliceResponseDto.of(
            places
                .map(place -> {
                    String imageUrl = Objects.isNull(imageMap.get(place.getId())) ? null :
                        imageMap.get(place.getId())
                            .stream()
                            .filter(Objects::nonNull)
                            .findFirst()
                            .map(TripRecordScheduleImageWithPlaceIdQueryDto::imageUrl)
                            .orElse(null);

                    return CityPlaceResponseDto.fromEntity(place, imageUrl);
                })
        );
    }

    // 도시 갤러리 리스트 조회
    @Transactional(readOnly = true)
    public List<CityImageContentResponseDto> getImages(Long cityId) {
        return tripRecordScheduleImageRepository.findByCityIdOrderByCreatedAtDescLimitSize(cityId, CITY_MEDIA_CONTENT_SIZE)
            .stream()
            .map(CityImageContentResponseDto::fromEntity)
            .toList();
    }

    // 도시 갤러리 리스트 조회 (페이징, 정렬)
    @Transactional(readOnly = true)
    public SliceResponseDto<CityImageContentResponseDto> getImages(Long cityId, Pageable pageable) {
        return SliceResponseDto.of(
            tripRecordScheduleImageRepository.findByCityId(cityId, pageable)
                .map(CityImageContentResponseDto::fromEntity)
        );
    }

    // 도시 쇼츠 리스트 조회
    @Transactional(readOnly = true)
    public List<TripRecordScheduleVideoListItemResponseDto> getVideos(Long cityId) {
        return tripRecordScheduleVideoRepository
            .findByCityIdOrderByCreatedAtDescLimitSize(cityId, CITY_MEDIA_CONTENT_SIZE)
            .stream()
            .map(TripRecordScheduleVideoListItemResponseDto::fromQueryDto)
            .toList();
    }

    // 도시 쇼츠 리스트 조회 (페이징, 정렬)
    @Transactional(readOnly = true)
    public SliceResponseDto<TripRecordScheduleVideoListItemResponseDto> getVideos(Long cityId, Pageable pageable) {
        return SliceResponseDto.of(
            tripRecordScheduleVideoRepository
                .findByCityId(cityId, pageable)
                .map(TripRecordScheduleVideoListItemResponseDto::fromQueryDto)
        );
    }

    private City getCityEntity(Long id) {
        return cityRepository.findById(id)
            .orElseThrow(CityNotFoundException::new);
    }

    private Map<Long, List<TripRecordScheduleImageWithPlaceIdQueryDto>> getImageMapFromPlaceIds(List<Place> places) {
        List<Long> placeIds = places.stream().map(Place::getId).toList();

        return tripRecordScheduleImageRepository
            .findInPlaceIdsOrderByCreatedAtDesc(placeIds)
            .stream()
            .collect(Collectors.groupingBy(TripRecordScheduleImageWithPlaceIdQueryDto::placeId));
    }
}
