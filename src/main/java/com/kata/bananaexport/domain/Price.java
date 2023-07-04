package com.kata.bananaexport.domain;


public record Price(Double amount, String currency) {

    public static Price ofEuros(final Double amount){
        return new Price(amount, "Euro");
    }
}
