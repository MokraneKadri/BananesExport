package com.kata.bananaexport.domain;

import com.kata.bananaexport.dto.OrderDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Builder
public class Order {

    private static final Double UNIT_PRICE = 2.5;

    private String orderId;

    private Recipient recipient;

    private Quantity quantity;

    private Price price;

    private LocalDateTime deliveryDate;


    public Order newInstanceWith(OrderDto payload) {
        return Order.builder()
                .orderId(getOrderId())
                .price(Price.ofEuros(computePrice(payload)))
                .quantity(Quantity.ofKilos(payload.quantity()))
                .deliveryDate(payload.deliveryDate())
                .recipient(payload.recipient())
                .build();
    }

    public static Order of(OrderDto payload) {
        return Order
                .builder()
                .price(Price.ofEuros(computePrice(payload)))
                .quantity(Quantity.ofKilos(payload.quantity()))
                .deliveryDate(payload.deliveryDate())
                .recipient(payload.recipient())
                .build();
    }

    private static double computePrice(OrderDto payload) {
        return payload.quantity() * UNIT_PRICE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return getRecipient().equals(order.getRecipient()) && getQuantity().equals(order.getQuantity()) && getPrice().equals(order.getPrice()) && getDeliveryDate().equals(order.getDeliveryDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRecipient(), getQuantity(), getPrice(), getDeliveryDate());
    }
}
