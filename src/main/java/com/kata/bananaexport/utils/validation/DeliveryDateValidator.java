package com.kata.bananaexport.utils.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class DeliveryDateValidator implements ConstraintValidator<ValidQuantity, LocalDateTime> {


    @Override
    public boolean isValid(final LocalDateTime deliveryDate, final ConstraintValidatorContext constraintValidatorContext) {
        LocalDateTime dateInSevenDay = LocalDateTime.now().plusDays(7);
        return dateInSevenDay.isBefore(deliveryDate) || dateInSevenDay.isEqual(deliveryDate);
    }
}
