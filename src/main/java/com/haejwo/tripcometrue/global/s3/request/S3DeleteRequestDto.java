package com.haejwo.tripcometrue.global.s3.request;

public record S3DeleteRequestDto(
        String url
){
    public S3DeleteRequestDto(String url) {
        this.url = url;
    }
}
