package com.example.demoadminpanel.user.model;

import com.example.demoadminpanel.user.model.enums.UserRole;
import com.example.demoadminpanel.user.model.enums.UserStatus;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateUserRequest {
    @NotNull(message = "Name should not be null")
    @Size(max = 25, message = "Name should be less than characters")
    private String name;

    @NotNull(message = "Username should not be null")
    @Size(max = 20, message = "Username should be less than 20 characters")
    private String username;

    @NotNull(message = "Password should not be null")
    @Size(max = 20, message = "Password should be less than 20 characters")
    private String password;

    @NotNull(message = "IPAddress should not be null")
    @Size(max = 25, message = "IPAddress should be less than 20 characters")
    private String ipAddress;

    @NotNull(message = "ReturnURL should not be null")
    @Size(max = 100, message = "ReturnURL should be less than 20 characters")
    private String returnUrl;

    @NotNull(message = "Role should not be null")
    private UserRole role;

    @NotNull(message = "Status should not be null")
    private UserStatus status;
}
