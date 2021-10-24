package com.example.demoadminpanel.exception;

public class ForbiddenRequestException extends RuntimeException{
    public ForbiddenRequestException(String message) {
        super(message);
    }
}
