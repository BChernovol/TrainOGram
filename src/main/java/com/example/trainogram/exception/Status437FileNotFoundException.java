package com.example.trainogram.exception;

public class Status437FileNotFoundException extends ErrorCodeException{

    private static final int CODE = 437;

    public Status437FileNotFoundException(String message) {
        super(CODE, message);
    }
}
