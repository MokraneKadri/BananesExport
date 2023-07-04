package com.kata.bananaexport.utils.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class QuantityValidator implements ConstraintValidator<ValidQuantity, Long> {

    private static final Long MIN_QUANTITY = 0L;
    private static final Long MAX_QUANTITY = 10_000L;
    public static final long PACKAGE_WEIGHT = 25L;

    @Override
    public boolean isValid(Long quantity, ConstraintValidatorContext constraintValidatorContext) {
        return (quantity <MAX_QUANTITY && quantity> MIN_QUANTITY)  && (quantity % PACKAGE_WEIGHT ==0);
    }
}
