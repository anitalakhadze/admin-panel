package com.example.demoadminpanel.exception.custom;

public class ForbiddenRequestException extends RuntimeException {

    public ForbiddenRequestException(String message) {
        super(message);
    }

}
