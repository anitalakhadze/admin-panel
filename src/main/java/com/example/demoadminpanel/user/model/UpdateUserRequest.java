package com.example.demoadminpanel.user.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UpdateUserRequest {
    @Size(max = 25, message = "Name should be less than characters")
    @NotNull(message = "Name should not be null")
    private String name;

    @Size(max = 25, message = "IPAddress should be less than 20 characters")
    @NotNull(message = "IPAddress should not be null")
    private String ipAddress;

    @Size(max = 100, message = "ReturnURL should be less than 20 characters")
    @NotNull(message = "ReturnURL should not be null")
    private String returnUrl;
}
