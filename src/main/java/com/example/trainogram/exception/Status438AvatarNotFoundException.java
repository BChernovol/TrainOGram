package com.example.trainogram.exception;

public class Status438AvatarNotFoundException extends ErrorCodeException{

    private final static int CODE = 438;
    public Status438AvatarNotFoundException(String message) {
        super(CODE, message);
    }
}
