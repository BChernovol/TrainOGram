package com.example.trainogram.exception;

public class Status420CommentPostIdNotFoundException extends ErrorCodeException{

    public static final int CODE = 420;

    public Status420CommentPostIdNotFoundException(String message) {
        super(CODE, message + " not found", "420");
    }
}
