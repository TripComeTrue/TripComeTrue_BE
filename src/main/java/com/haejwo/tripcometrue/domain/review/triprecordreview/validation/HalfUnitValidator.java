package com.haejwo.tripcometrue.domain.review.triprecordreview.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HalfUnitValidator implements ConstraintValidator<HalfUnit, Float> {

    @Override
    public boolean isValid(Float ratingScore, ConstraintValidatorContext context) {
        return ratingScore % 0.5 == 0;
    }
}
