package com.haejwo.tripcometrue.global.s3.controller;

import com.haejwo.tripcometrue.global.s3.request.S3DeleteRequestDto;
import com.haejwo.tripcometrue.global.s3.response.S3UploadResponseDto;
import com.haejwo.tripcometrue.global.s3.service.S3Service;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Component
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping("/v1/images")
    public ResponseEntity<ResponseDTO<S3UploadResponseDto>> uploadImage(
            @RequestPart("file") MultipartFile multipartFile) throws IOException {
        ResponseDTO<S3UploadResponseDto> responseDto = ResponseDTO.okWithData(s3Service.saveImage(multipartFile));
        return ResponseEntity.status(responseDto.getCode()).body(responseDto);
    }

    @DeleteMapping("/v1/images")
    public void deleteImage(@RequestBody S3DeleteRequestDto requestDto) {
        s3Service.removeImage(requestDto.url());
    }
}
