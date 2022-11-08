package com.example.trainogram.exception;

public class Status433LikeAlreadyExistsException extends ErrorCodeException{

    public static final int CODE = 433;

    public Status433LikeAlreadyExistsException(String message) {
        super(CODE, message);
    }
}
