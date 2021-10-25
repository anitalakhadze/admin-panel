package com.example.demoadminpanel.exception.customExceptions;

public class ForbiddenRequestException extends Exception{
    public ForbiddenRequestException(String message) {
        super(message);
    }
}
