package com.example.demoadminpanel.exception.customExceptions;

public class ForbiddenRequestException extends RuntimeException{
    public ForbiddenRequestException(String message) {
        super(message);
    }
}
