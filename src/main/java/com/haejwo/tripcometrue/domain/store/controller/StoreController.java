package com.haejwo.tripcometrue.domain.store.controller;

import com.haejwo.tripcometrue.domain.store.dto.request.CityStoreRequestDto;
import com.haejwo.tripcometrue.domain.store.dto.request.PlaceStoreRequestDto;
import com.haejwo.tripcometrue.domain.store.dto.request.TripRecordStoreRequestDto;
import com.haejwo.tripcometrue.domain.store.dto.response.CheckCityStoredResponseDto;
import com.haejwo.tripcometrue.domain.store.dto.response.CityStoreResponseDto;
import com.haejwo.tripcometrue.domain.store.dto.response.PlaceStoreResponseDto;
import com.haejwo.tripcometrue.domain.store.dto.response.TripRecordStoreResponseDto;
import com.haejwo.tripcometrue.domain.store.service.StoreService;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/v1/cities/stores")
    public ResponseEntity<ResponseDTO<CityStoreResponseDto>> storeCity(
        @RequestBody CityStoreRequestDto request,
        @AuthenticationPrincipal PrincipalDetails principalDetails) {
        CityStoreResponseDto response = storeService.storeCity(request, principalDetails);
        return ResponseEntity.ok(ResponseDTO.okWithData(response));
    }

    @PostMapping("/v1/places/stores")
    public ResponseEntity<ResponseDTO<PlaceStoreResponseDto>> storePlace(
        @RequestBody PlaceStoreRequestDto request,
        @AuthenticationPrincipal PrincipalDetails principalDetails) {
        PlaceStoreResponseDto response = storeService.storePlace(request, principalDetails);
        return ResponseEntity.ok(ResponseDTO.okWithData(response));
    }

    @PostMapping("/v1/trip-records/stores")
    public ResponseEntity<ResponseDTO<TripRecordStoreResponseDto>> storeTripRecord(
        @RequestBody TripRecordStoreRequestDto request,
        @AuthenticationPrincipal PrincipalDetails principalDetails) {
        TripRecordStoreResponseDto response = storeService.storeTripRecord(request, principalDetails);
        return ResponseEntity.ok(ResponseDTO.okWithData(response));
    }

    @DeleteMapping("/v1/cities/{cityId}/stores")
    public ResponseEntity<ResponseDTO<Void>> unstoreCity(@PathVariable Long cityId,
        @AuthenticationPrincipal PrincipalDetails principalDetails) {
        storeService.unstoreCity(principalDetails, cityId);
        return ResponseEntity.ok(ResponseDTO.ok());
    }

    @DeleteMapping("/v1/places/{placeId}/stores")
    public ResponseEntity<ResponseDTO<Void>> unstorePlace(@PathVariable Long placeId,
        @AuthenticationPrincipal PrincipalDetails principalDetails) {
        storeService.unstorePlace(principalDetails, placeId);
        return ResponseEntity.ok(ResponseDTO.ok());
    }

    @DeleteMapping("/v1/trip-records/{tripRecordId}/stores")
    public ResponseEntity<ResponseDTO<Void>> unstoreTripRecord(@PathVariable Long tripRecordId,
        @AuthenticationPrincipal PrincipalDetails principalDetails) {
        storeService.unstoreTripRecord(principalDetails, tripRecordId);
        return ResponseEntity.ok(ResponseDTO.ok());
    }

    @GetMapping("/v1/cities/stores")
    public ResponseEntity<ResponseDTO<Page<CityStoreResponseDto>>> getStoredCities(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CityStoreResponseDto> storedCities = storeService.getStoredCities(principalDetails, pageable);
        return ResponseEntity.ok(ResponseDTO.okWithData(storedCities));
    }

    @GetMapping("/v1/places/stores")
    public ResponseEntity<ResponseDTO<Page<PlaceStoreResponseDto>>> getStoredPlaces(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PlaceStoreResponseDto> storedPlaces = storeService.getStoredPlaces(principalDetails, pageable);
        return ResponseEntity.ok(ResponseDTO.okWithData(storedPlaces));
    }

    @GetMapping("/v1/trip-records/stores")
    public ResponseEntity<ResponseDTO<Page<TripRecordStoreResponseDto>>> getStoredTripRecords(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<TripRecordStoreResponseDto> storedTripRecords = storeService.getStoredTripRecords(principalDetails, pageable);
        return ResponseEntity.ok(ResponseDTO.okWithData(storedTripRecords));
    }

    @GetMapping("/v1/cities/{cityId}/stores/count")
    public ResponseEntity<ResponseDTO<Long>> getStoredCountForCity(@PathVariable Long cityId) {
        Long count = storeService.getStoredCountForCity(cityId);
        return ResponseEntity.ok(ResponseDTO.okWithData(count));
    }

    @GetMapping("/v1/places/{placeId}/stores/count")
    public ResponseEntity<ResponseDTO<Long>> getStoredCountForPlace(@PathVariable Long placeId) {
        Long count = storeService.getStoredCountForPlace(placeId);
        return ResponseEntity.ok(ResponseDTO.okWithData(count));
    }

    @GetMapping("/v1/trip-records/{tripRecordId}/stores/count")
    public ResponseEntity<ResponseDTO<Long>> getStoredCountForTripRecord(@PathVariable Long tripRecordId) {
        Long count = storeService.getStoredCountForTripRecord(tripRecordId);
        return ResponseEntity.ok(ResponseDTO.okWithData(count));
    }

    @GetMapping("/v1/cities/{cityId}/stores")
    public ResponseEntity<ResponseDTO<CheckCityStoredResponseDto>> checkCityStoredByLoginMember(
        @PathVariable("cityId") Long cityId,
        @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {

        return ResponseEntity
            .ok()
            .body(
                ResponseDTO.okWithData(
                    storeService.checkCityStoredByLoginMember(principalDetails, cityId)
                )
            );
    }
}