package com.haejwo.tripcometrue.domain.review.triprecordreview.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD}) // 변수에 사용
@Retention(RetentionPolicy.RUNTIME) // 에노테이션 유지범위
@Constraint(validatedBy = HalfUnitValidator.class) // 검증 클래스
public @interface HalfUnit {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
