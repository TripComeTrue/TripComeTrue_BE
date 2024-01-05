package com.haejwo.tripcometrue.global.s3.controller;

import com.haejwo.tripcometrue.global.s3.response.S3ResponseDto;
import com.haejwo.tripcometrue.global.s3.service.S3Service;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Component
public class S3Controller {

    private final S3Service s3Utils;

    @PostMapping("/v1/images")
    public ResponseEntity<ResponseDTO<S3ResponseDto>> uploadImage(
            @RequestPart("file") MultipartFile multipartFile) throws IOException {
        ResponseDTO<S3ResponseDto> responseDto = ResponseDTO.okWithData(s3Utils.saveImage(multipartFile));
        return ResponseEntity.status(responseDto.getCode()).body(responseDto);
    }

}
