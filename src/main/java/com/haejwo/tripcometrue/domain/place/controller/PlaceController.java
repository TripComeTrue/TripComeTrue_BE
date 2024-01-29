package com.haejwo.tripcometrue.domain.place.controller;

import com.haejwo.tripcometrue.domain.place.dto.request.PlaceRequestDto;
import com.haejwo.tripcometrue.domain.place.dto.response.PlaceListItemResponseDto;
import com.haejwo.tripcometrue.domain.place.dto.response.PlaceMapInfoResponseDto;
import com.haejwo.tripcometrue.domain.place.dto.response.PlaceNearbyResponseDto;
import com.haejwo.tripcometrue.domain.place.dto.response.PlaceResponseDto;
import com.haejwo.tripcometrue.domain.place.service.PlaceService;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import java.util.List;

import com.haejwo.tripcometrue.global.util.SliceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/places")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    @PostMapping
    public ResponseEntity<ResponseDTO<PlaceResponseDto>> placeAdd(
        @RequestBody PlaceRequestDto requestDto
    ) {

        PlaceResponseDto responseDto = placeService.addPlace(requestDto);
        ResponseDTO<PlaceResponseDto> responseBody = ResponseDTO.okWithData(responseDto);

        return ResponseEntity
            .status(responseBody.getCode())
            .body(responseBody);
    }

    @GetMapping("/{placeId}")
    public ResponseEntity<ResponseDTO<PlaceResponseDto>> placeDetails(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable Long placeId
    ) {

        PlaceResponseDto responseDto = placeService.findPlace(principalDetails, placeId);
        ResponseDTO<PlaceResponseDto> responseBody = ResponseDTO.okWithData(responseDto);

        return ResponseEntity
            .status(responseBody.getCode())
            .body(responseBody);
    }

    @GetMapping("/{placeId}/maplist")
    public ResponseEntity<ResponseDTO<List<PlaceMapInfoResponseDto>>> placeMapInfoList(
        @PathVariable Long placeId
    ) {

        List<PlaceMapInfoResponseDto> responseDtos = placeService.findPlaceMapInfoList(placeId);

        ResponseDTO<List<PlaceMapInfoResponseDto>> responseBody = ResponseDTO.okWithData(responseDtos);

        return ResponseEntity
            .status(responseBody.getCode())
            .body(responseBody);
    }

    @GetMapping("/{placeId}/nearby")
    public ResponseEntity<ResponseDTO<List<PlaceNearbyResponseDto>>> placeNearbyList(
        @PathVariable Long placeId
    ) {

        List<PlaceNearbyResponseDto> responseDtos = placeService.findNearbyPlaceList(placeId);

        ResponseDTO<List<PlaceNearbyResponseDto>> responseBody = ResponseDTO.okWithData(responseDtos);

        return ResponseEntity.status(responseBody
                .getCode())
                .body(responseBody);
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<Page<PlaceResponseDto>>> placeList(
        Pageable pageable,
        @RequestParam Integer storedCount
    ) {

        Page<PlaceResponseDto> placePage = placeService.findPlaces(pageable, storedCount);

        ResponseDTO responseBody = ResponseDTO.okWithData(placePage);

        return ResponseEntity
            .status(responseBody.getCode())
            .body(responseBody);
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseDTO<SliceResponseDto<PlaceListItemResponseDto>>> searchPlacesByName(
        @RequestParam("placeName") String placeName,
        @PageableDefault
        @SortDefault.SortDefaults({
                @SortDefault(sort = "storedCount", direction = Sort.Direction.DESC),
                @SortDefault(sort = "commentCount", direction = Sort.Direction.DESC)})
        Pageable pageable
    ) {
        return ResponseEntity
            .ok()
            .body(
                ResponseDTO.okWithData(
                    placeService.listPlacesByName(placeName, pageable)
                )
            );
    }

    @PutMapping("/{placeId}")
    public ResponseEntity<ResponseDTO<PlaceResponseDto>> placeModify(
        @PathVariable Long placeId,
        @RequestBody PlaceRequestDto requestDto
    ) {

        PlaceResponseDto responseDto = placeService.modifyPlace(placeId, requestDto);
        ResponseDTO<PlaceResponseDto> responseBody = ResponseDTO.okWithData(responseDto);

        return ResponseEntity
            .status(responseBody.getCode())
            .body(responseBody);
    }

    @DeleteMapping("/{placeId}")
    public ResponseEntity<ResponseDTO> placeRemove(
        @PathVariable Long placeId
    ) {

        placeService.removePlace(placeId);
        ResponseDTO responseBody = ResponseDTO.ok();

        return ResponseEntity
            .status(responseBody.getCode())
            .body(responseBody);
    }

}
