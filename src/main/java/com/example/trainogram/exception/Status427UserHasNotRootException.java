package com.example.trainogram.exception;

public class Status427UserHasNotRootException extends ErrorCodeException {

    public static final int CODE = 427;

    public Status427UserHasNotRootException(String message) {
        super(CODE, message);
    }
}
