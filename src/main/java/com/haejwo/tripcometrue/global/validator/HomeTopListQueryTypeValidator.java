package com.haejwo.tripcometrue.global.validator;

import com.haejwo.tripcometrue.global.validator.annotation.HomeTopListQueryType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class HomeTopListQueryTypeValidator implements ConstraintValidator<HomeTopListQueryType, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (Objects.isNull(value)) {
            return false;
        }

        return value.equalsIgnoreCase("overseas") || value.equalsIgnoreCase("domestic");
    }
}
