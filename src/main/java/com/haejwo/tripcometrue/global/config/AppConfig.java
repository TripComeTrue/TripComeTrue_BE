package com.haejwo.tripcometrue.global.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author liyusang1
 * @implNote 오직 Bean 등록 하나만 하는 등의 단순 설정을 모아두는 클래스 입니다.
 */

@Configuration
public class AppConfig {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public ObjectMapper objectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        // RestController에서 json 응답 시 null 값의 필드는 아예 보여주지 않도록 설정하는 부분
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//        return objectMapper;
//    }
}
