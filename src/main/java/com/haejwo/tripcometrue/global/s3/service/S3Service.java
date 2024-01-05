package com.haejwo.tripcometrue.global.s3.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.haejwo.tripcometrue.global.s3.exception.FileEmptyException;
import com.haejwo.tripcometrue.global.s3.exception.FileUploadFailException;
import com.haejwo.tripcometrue.global.s3.response.S3ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class S3Service {
    private final AmazonS3Client amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    // TODO: 1/6/24 파일 용량 제한 걸기
    // TODO: 1/6/24 uuid로 고유 파일명 만들기
    // TODO: 1/6/24 저장 디렉토리 분리하기
    public S3ResponseDto saveImage(MultipartFile multipartFile) throws IOException {
        validateFileExists(multipartFile);

        String originalFilename = multipartFile.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3.putObject(bucketName, originalFilename, inputStream, metadata);
        } catch (IOException e) {
            throw new FileUploadFailException();
        }

        return new S3ResponseDto(amazonS3.getUrl(bucketName, originalFilename).toString());
    }

    private void validateFileExists(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new FileEmptyException();
        }
    }
}
