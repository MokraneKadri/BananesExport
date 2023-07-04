package com.kata.bananaexport.utils.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DeliveryDateValidator.class)
@Documented
public @interface ValidDeliveryDate {

    String message() default "Delivery date must be at least one week starting from today!";

    Class<?> [] groups() default {};

    Class<? extends Payload> [] payload() default {};
}
