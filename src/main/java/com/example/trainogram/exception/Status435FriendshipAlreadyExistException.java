package com.example.trainogram.exception;

public class Status435FriendshipAlreadyExistException extends ErrorCodeException{

    private static final int CODE = 435;

    public Status435FriendshipAlreadyExistException(String message) {
        super(CODE, message);
    }
}
