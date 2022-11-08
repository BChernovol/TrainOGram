package com.example.trainogram.exception;

import org.aspectj.apache.bcel.classfile.Code;

public class Status430UserNotFoundException extends ErrorCodeException{
    public final static int CODE = 430;

    public Status430UserNotFoundException(String message) {
        super(CODE, message);
    }
}
