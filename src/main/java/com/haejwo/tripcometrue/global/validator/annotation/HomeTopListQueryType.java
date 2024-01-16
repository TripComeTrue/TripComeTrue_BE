package com.haejwo.tripcometrue.global.validator.annotation;

import com.haejwo.tripcometrue.global.validator.HomeTopListQueryTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = HomeTopListQueryTypeValidator.class)
public @interface HomeTopListQueryType {
    String message() default "올바른 검색 타입이 아닙니다. [overseas, domestic] 중 하나를 입력하세요.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
