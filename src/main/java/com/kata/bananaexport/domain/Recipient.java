package com.kata.bananaexport.domain;


import com.kata.bananaexport.dto.RecipientDto;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public class Recipient {

    private String recipientId;

    private final String name;

    private final String address;

    private final String zipCode;

    private final String city;

    private final String country;

    public static Recipient of(RecipientDto payload) {
        return Recipient
                .builder()
                .name(payload.name())
                .address(payload.address())
                .zipCode(payload.zipCode())
                .city(payload.city())
                .country(payload.country())
                .build();
    }

    public Recipient newInstanceWith(RecipientDto payload) {
        return new Recipient(getRecipientId(),
                payload.name(),
                payload.address(),
                payload.zipCode(),
                payload.city(),
                payload.country());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipient recipient = (Recipient) o;
        return getName().equals(recipient.getName()) && getAddress().equals(recipient.getAddress()) && getZipCode().equals(recipient.getZipCode()) && getCity().equals(recipient.getCity()) && getCountry().equals(recipient.getCountry());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAddress(), getZipCode(), getCity(), getCountry());
    }
}
