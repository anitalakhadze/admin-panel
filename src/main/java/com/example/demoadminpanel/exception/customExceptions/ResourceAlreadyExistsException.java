package com.example.demoadminpanel.exception.customExceptions;

public class ResourceAlreadyExistsException extends Exception{
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
