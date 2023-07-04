package com.kata.bananaexport.utils;

public class OrderManagementException extends Exception{

    public OrderManagementException(String message) {
        super(message);
    }

    public static OrderManagementException noSuchOrder(){
        return new OrderManagementException("Requested Order could not be found!");
    }
}
