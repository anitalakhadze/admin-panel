package com.example.demoadminpanel.exception;

import com.example.demoadminpanel.exception.customExceptions.ForbiddenRequestException;
import com.example.demoadminpanel.exception.customExceptions.GeneralApiException;
import com.example.demoadminpanel.exception.customExceptions.ResourceAlreadyExistsException;
import com.example.demoadminpanel.exception.customExceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.xml.bind.ValidationException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage resourceNotFoundException(ResourceNotFoundException ex) {
        return new ErrorMessage(new Date(), ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ResourceAlreadyExistsException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage resourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        return new ErrorMessage(new Date(), ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ForbiddenRequestException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ErrorMessage forbiddenRequestException(ForbiddenRequestException ex) {
        return new ErrorMessage(new Date(), ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        StringBuilder sb = new StringBuilder();
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        for (ObjectError objectError : allErrors) {
            String defaultMessage = objectError.getDefaultMessage();
            sb.append(defaultMessage).append("; ");
        }
        return new ErrorMessage(new Date(), sb.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = GeneralApiException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage generalApiException(GeneralApiException ex) {
        return new ErrorMessage(new Date(), ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
