package com.example.trainogram.exception;

public class Status432UserCannotAddOneselfToFriend extends ErrorCodeException{

    public static final int CODE = 432;

    public Status432UserCannotAddOneselfToFriend(String message) {
        super(CODE, message);

    }
}
