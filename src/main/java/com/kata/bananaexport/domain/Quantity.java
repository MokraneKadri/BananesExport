package com.kata.bananaexport.domain;

public record Quantity(Long weight, String unit) {

    public static Quantity ofKilos(Long weight){
        return new Quantity(weight, "Kilogram");
    }
}
