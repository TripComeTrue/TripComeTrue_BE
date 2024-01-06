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
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3Service {
    private final AmazonS3Client amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    // TODO: 1/6/24 파일 용량 제한 걸기
    // TODO: 1/6/24 저장 디렉토리 분리하기
    public S3ResponseDto saveImage(MultipartFile multipartFile) {
        validateFileExists(multipartFile);
        String filename = generateFilename(multipartFile);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3.putObject(bucketName, filename, inputStream, metadata);
        } catch (IOException e) {
            throw new FileUploadFailException();
        }

        return new S3ResponseDto(amazonS3.getUrl(bucketName, filename).toString());
    }

    private String generateFilename(MultipartFile multipartFile) {
        return UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
    }

    // TODO: 1/6/24 파일이 존재하지 않을 경우 예외 발생 추가
    public void removeImage(String url) {
        String filename = getFilename(url);
        amazonS3.deleteObject(bucketName, filename);
    }

    //디코드를 해야지 제대로 삭제가 가능하다. 객체지향적으로 바꾸도록 해보자.
    private String getFilename(String url) {
        String encodedName = url.substring(url.lastIndexOf("/") + 1);
        try {
            return URLDecoder.decode(encodedName, StandardCharsets.UTF_8.toString());
        } catch (Exception e) {}
        return "";
    }

    private void validateFileExists(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new FileEmptyException();
        }
    }
}
