package com.haejwo.tripcometrue.global.validator;

import com.haejwo.tripcometrue.global.validator.annotation.HomeVideoListQueryType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class HomeVideoListQueryTypeValidator implements ConstraintValidator<HomeVideoListQueryType, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) {
            return false;
        }

        return value.equalsIgnoreCase("all")
            || value.equalsIgnoreCase("domestic")
            || value.equalsIgnoreCase("overseas");
    }
}
