package com.kata.bananaexport.utils;

public class RecipientManagementException extends Exception{

    public RecipientManagementException(String message) {
        super(message);
    }

    public static RecipientManagementException noSuchRecipient(){
        return new RecipientManagementException("Requested Recipient could not be found!");
    }
}
