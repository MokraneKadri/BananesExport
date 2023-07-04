package com.kata.bananaexport.utils.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = QuantityValidator.class)
@Documented
public @interface ValidQuantity {

    String message() default "Quantity value cannot be less than 0 or exceed 10 000 and must be a multiplier of 25";

    Class<?> [] groups() default {};

    Class<? extends Payload> [] payload() default {};
}
