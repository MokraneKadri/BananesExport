package com.kata.bananaexport.utils.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueRecipientValidator.class)
@Documented
public @interface UniqueRecipient {

    String message() default "A recipient must always be unique!";

    Class<?> [] groups() default {};

    Class<? extends Payload> [] payload() default {};
}
