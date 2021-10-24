package com.example.demoadminpanel.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorMessage {
    private Date timestamp;
    private String errorMessage;
    private HttpStatus httpStatus;
}
