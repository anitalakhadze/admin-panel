package com.example.demoadminpanel.exception;

import com.example.demoadminpanel.exception.customExceptions.ForbiddenRequestException;
import com.example.demoadminpanel.exception.customExceptions.ResourceAlreadyExistsException;
import com.example.demoadminpanel.exception.customExceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage resourceNotFoundException(ResourceNotFoundException ex) {
        return new ErrorMessage(new Date(), ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ResourceAlreadyExistsException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage resourceNotFoundException(ResourceAlreadyExistsException ex) {
        return new ErrorMessage(new Date(), ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ForbiddenRequestException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ErrorMessage resourceNotFoundException(ForbiddenRequestException ex) {
        return new ErrorMessage(new Date(), ex.getMessage(), HttpStatus.FORBIDDEN);
    }
}
