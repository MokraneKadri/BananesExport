package com.kata.bananaexport.utils.validation;

import com.kata.bananaexport.domain.Recipient;
import com.kata.bananaexport.repository.RecipientInMemoryRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueRecipientValidator implements ConstraintValidator<ValidQuantity, Recipient> {

        @Autowired
        private RecipientInMemoryRepository recipientInMemoryRepository;

        @Override
        public boolean isValid(Recipient recipient, ConstraintValidatorContext constraintValidatorContext) {
                return recipientInMemoryRepository
                        .findAll()
                        .stream()
                        .noneMatch(e -> e.equals(recipient));
        }
}