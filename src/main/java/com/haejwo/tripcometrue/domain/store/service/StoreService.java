package com.haejwo.tripcometrue.domain.store.service;

import com.haejwo.tripcometrue.domain.city.entity.City;
import com.haejwo.tripcometrue.domain.city.exception.CityNotFoundException;
import com.haejwo.tripcometrue.domain.city.repository.CityRepository;
import com.haejwo.tripcometrue.domain.place.entity.Place;
import com.haejwo.tripcometrue.domain.place.exception.PlaceNotFoundException;
import com.haejwo.tripcometrue.domain.place.repositroy.PlaceRepository;
import com.haejwo.tripcometrue.domain.store.dto.request.CityStoreRequestDto;
import com.haejwo.tripcometrue.domain.store.dto.request.PlaceStoreRequestDto;
import com.haejwo.tripcometrue.domain.store.dto.request.TripRecordStoreRequestDto;
import com.haejwo.tripcometrue.domain.store.dto.response.CityStoreResponseDto;
import com.haejwo.tripcometrue.domain.store.dto.response.PlaceStoreResponseDto;
import com.haejwo.tripcometrue.domain.store.dto.response.TripRecordStoreResponseDto;
import com.haejwo.tripcometrue.domain.store.entity.CityStore;
import com.haejwo.tripcometrue.domain.store.entity.PlaceStore;
import com.haejwo.tripcometrue.domain.store.entity.TripRecordStore;
import com.haejwo.tripcometrue.domain.store.exception.StoreAlreadyExistException;
import com.haejwo.tripcometrue.domain.store.exception.StoreNotFoundException;
import com.haejwo.tripcometrue.domain.store.repository.CityStoreRepository;
import com.haejwo.tripcometrue.domain.store.repository.PlaceStoreRepository;
import com.haejwo.tripcometrue.domain.store.repository.TripRecordStoreRepository;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordImage;
import com.haejwo.tripcometrue.domain.triprecord.exception.TripRecordNotFoundException;
import com.haejwo.tripcometrue.domain.triprecord.repository.triprecord.TripRecordRepository;
import com.haejwo.tripcometrue.global.exception.ErrorCode;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService{

    private final CityRepository cityRepository;
    private final PlaceRepository placeRepository;
    private final TripRecordRepository tripRecordRepository;
    private final CityStoreRepository cityStoreRepository;
    private final PlaceStoreRepository placeStoreRepository;
    private final TripRecordStoreRepository tripRecordStoreRepository;


    @Transactional
    public CityStoreResponseDto storeCity(CityStoreRequestDto request, PrincipalDetails principalDetails) {
        City city = cityRepository.findById(request.cityId())
            .orElseThrow(() -> new CityNotFoundException());

        cityStoreRepository.findByMemberAndCity(principalDetails.getMember(), city)
            .ifPresent(store -> {
                throw new StoreAlreadyExistException(ErrorCode.STORE_ALREADY_EXIST);
            });

        city.incrementStoreCount();
        cityRepository.save(city);

        CityStore store = cityStoreRepository.save(request.toEntity(principalDetails.getMember(), city));
        return CityStoreResponseDto.fromEntity(store);
    }

    @Transactional
    public PlaceStoreResponseDto storePlace(PlaceStoreRequestDto request, PrincipalDetails principalDetails) {
        Place place = placeRepository.findById(request.placeId())
            .orElseThrow(() -> new PlaceNotFoundException());

        placeStoreRepository.findByMemberAndPlace(principalDetails.getMember(), place)
            .ifPresent(store -> {
                throw new StoreAlreadyExistException(ErrorCode.STORE_ALREADY_EXIST);
            });

        place.incrementStoreCount();
        placeRepository.save(place);

        PlaceStore store = placeStoreRepository.save(request.toEntity(principalDetails.getMember(), place));
        return PlaceStoreResponseDto.fromEntity(store);
    }

    @Transactional
    public TripRecordStoreResponseDto storeTripRecord(TripRecordStoreRequestDto request, PrincipalDetails principalDetails) {
        TripRecord tripRecord = tripRecordRepository.findById(request.tripRecordId())
            .orElseThrow(() -> new TripRecordNotFoundException());

        tripRecordStoreRepository.findByMemberAndTripRecord(principalDetails.getMember(), tripRecord)
            .ifPresent(store -> {
                throw new StoreAlreadyExistException(ErrorCode.STORE_ALREADY_EXIST);
            });

        tripRecord.incrementStoreCount();
        tripRecordRepository.save(tripRecord);

        TripRecordStore store = tripRecordStoreRepository.save(request.toEntity(principalDetails.getMember(), tripRecord));
        List<TripRecordImage> images = tripRecord.getTripRecordImages();
        String imageUrl = images.isEmpty() ? null : images.get(0).getImageUrl();

        return TripRecordStoreResponseDto.fromEntity(store, imageUrl);
    }

    @Transactional
    public void unstoreCity(PrincipalDetails principalDetails, Long cityId) {
        Long memberId = principalDetails.getMember().getId();
        CityStore cityStore = cityStoreRepository.findByMemberIdAndCityId(memberId, cityId)
                .orElseThrow(() -> new StoreNotFoundException(ErrorCode.STORE_NOT_FOUND));
        cityStoreRepository.delete(cityStore);
    }

    @Transactional
    public void unstorePlace(PrincipalDetails principalDetails, Long placeId) {
        Long memberId = principalDetails.getMember().getId();
        PlaceStore placeStore = placeStoreRepository.findByMemberIdAndPlaceId(memberId, placeId)
                .orElseThrow(() -> new StoreNotFoundException(ErrorCode.STORE_NOT_FOUND));
        placeStoreRepository.delete(placeStore);
    }

    @Transactional
    public void unstoreTripRecord(PrincipalDetails principalDetails, Long tripRecordId) {
        Long memberId = principalDetails.getMember().getId();
        TripRecordStore tripRecordStore = tripRecordStoreRepository.findByMemberIdAndTripRecordId(memberId, tripRecordId)
                .orElseThrow(() -> new StoreNotFoundException(ErrorCode.STORE_NOT_FOUND));
        tripRecordStoreRepository.delete(tripRecordStore);
    }

    public Page<CityStoreResponseDto> getStoredCities(PrincipalDetails principalDetails, Pageable pageable) {
        Page<CityStore> storedCities = cityStoreRepository.findByMember(principalDetails.getMember(), pageable);
        return storedCities.map(CityStoreResponseDto::fromEntity);
    }

    public Page<PlaceStoreResponseDto> getStoredPlaces(PrincipalDetails principalDetails, Pageable pageable) {
        Page<PlaceStore> storedPlaces = placeStoreRepository.findByMember(principalDetails.getMember(), pageable);
        return storedPlaces.map(PlaceStoreResponseDto::fromEntity);
    }

    public Page<TripRecordStoreResponseDto> getStoredTripRecords(PrincipalDetails principalDetails, Pageable pageable) {
        Page<TripRecordStore> storedTripRecords = tripRecordStoreRepository.findByMember(principalDetails.getMember(), pageable);
        return storedTripRecords.map(tripRecordStore -> {
            TripRecord tripRecord = tripRecordStore.getTripRecord();
            List<TripRecordImage> images = tripRecord.getTripRecordImages();
            String imageUrl = images.isEmpty() ? null : images.get(0).getImageUrl();
            return TripRecordStoreResponseDto.fromEntity(tripRecordStore, imageUrl);
        });
    }

    public Long getStoredCountForCity(Long cityId) {
        City city = cityRepository.findById(cityId)
            .orElseThrow(() -> new CityNotFoundException());

        return cityStoreRepository.countByCity(city);
    }

    public Long getStoredCountForPlace(Long placeId) {
        Place place = placeRepository.findById(placeId)
            .orElseThrow(() -> new PlaceNotFoundException());

        return placeStoreRepository.countByPlace(place);
    }

    public Long getStoredCountForTripRecord(Long tripRecordId) {
        TripRecord tripRecord = tripRecordRepository.findById(tripRecordId)
            .orElseThrow(() -> new TripRecordNotFoundException());

        return tripRecordStoreRepository.countByTripRecord(tripRecord);
    }
}
