package com.example.trainogram.exception;

public class Status440UserCannotDeleteOneselfToFriend extends ErrorCodeException{

    public static final int CODE = 440;

    public Status440UserCannotDeleteOneselfToFriend(String message) {
        super(CODE, message);

    }
}
