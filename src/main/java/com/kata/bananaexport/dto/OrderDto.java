package com.kata.bananaexport.dto;

import com.kata.bananaexport.domain.Recipient;
import com.kata.bananaexport.utils.validation.ValidDeliveryDate;
import com.kata.bananaexport.utils.validation.ValidQuantity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record OrderDto(@Valid Recipient recipient,
                       @NotNull(message = "Quantity cannot be null")
                       @ValidQuantity Long quantity,


                       @NotNull(message = "Delivery Date cannot be null")
                       @ValidDeliveryDate
                       LocalDateTime deliveryDate) {

}
