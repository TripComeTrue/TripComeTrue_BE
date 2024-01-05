package com.haejwo.tripcometrue.global.s3.request;

public record S3RequestDto(
        String url
){
    public S3RequestDto(String url) {
        this.url = url;
    }
}
