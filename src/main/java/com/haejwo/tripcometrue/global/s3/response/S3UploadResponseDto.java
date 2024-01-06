package com.haejwo.tripcometrue.global.s3.response;

public record S3UploadResponseDto(
        String url
) {
    public S3UploadResponseDto(String url) {
        this.url = url;
    }
}
