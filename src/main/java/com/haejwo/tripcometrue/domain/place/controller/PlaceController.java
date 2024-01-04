package com.haejwo.tripcometrue.domain.place.controller;

import com.haejwo.tripcometrue.domain.place.dto.request.PlaceRequestDTO;
import com.haejwo.tripcometrue.domain.place.dto.response.PlaceResponseDTO;
import com.haejwo.tripcometrue.domain.place.service.PlaceService;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseDTO<PlaceResponseDTO>> placeAdd(
        @RequestBody PlaceRequestDTO requestDto
    ) {

        PlaceResponseDTO responseDto = placeService.addPlace(requestDto);
        ResponseDTO<PlaceResponseDTO> responseBody = ResponseDTO.okWithData(responseDto);

        return ResponseEntity
            .status(responseBody.getCode())
            .body(responseBody);
    }

    @GetMapping("/{placeId}")
    public ResponseEntity<ResponseDTO<PlaceResponseDTO>> placeDetails(
        @PathVariable Long placeId
    ) {

        PlaceResponseDTO responseDto = placeService.findPlace(placeId);
        ResponseDTO<PlaceResponseDTO> responseBody = ResponseDTO.okWithData(responseDto);

        return ResponseEntity
            .status(responseBody.getCode())
            .body(responseBody);
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<Page<PlaceResponseDTO>>> placeList(
        Pageable pageable,
        @RequestParam Integer storedCount
    ) {

        Page<PlaceResponseDTO> placePage = placeService.findPlaces(pageable, storedCount);

        ResponseDTO responseBody = ResponseDTO.okWithData(placePage);

        return ResponseEntity
            .status(responseBody.getCode())
            .body(responseBody);
    }

    @PutMapping("/{placeId}")
    public ResponseEntity<ResponseDTO<PlaceResponseDTO>> placeModify(
        @PathVariable Long placeId,
        @RequestBody PlaceRequestDTO requestDto
    ) {

        PlaceResponseDTO responseDto = placeService.modifyPlace(placeId, requestDto);
        ResponseDTO<PlaceResponseDTO> responseBody = ResponseDTO.okWithData(responseDto);

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
