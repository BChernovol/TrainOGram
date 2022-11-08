package com.example.trainogram.exception;

public class Status436PostNotFoundException extends ErrorCodeException{

    private static final int CODE = 436;

    public Status436PostNotFoundException(String message) {
        super(CODE, message);
    }
}
