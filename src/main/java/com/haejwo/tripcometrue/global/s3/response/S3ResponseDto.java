package com.haejwo.tripcometrue.global.s3.response;

public record S3ResponseDto (
        String url
) {
    public S3ResponseDto(String url) {
        this.url = url;
    }
}
