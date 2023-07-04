package com.kata.bananaexport.dto;

import com.kata.bananaexport.utils.validation.UniqueRecipient;
import jakarta.validation.constraints.NotEmpty;

@UniqueRecipient
public record RecipientDto(

        @NotEmpty(message = "Recipient Name is Required")
        String name,

        @NotEmpty(message = "Recipient Address is Required")
        String address,

        @NotEmpty(message = "Recipient Zip Code is Required")
        String zipCode,

        @NotEmpty(message = "Recipient City is Required")
        String city,

        @NotEmpty(message = "Recipient Country is Required")
        String country
) {
}
