package com.example.trainogram.exception;

public class Status439TextIsEmptyException extends ErrorCodeException{

    private final static int CODE = 439;
    public Status439TextIsEmptyException(String message) {
        super(CODE, message);
    }
}
