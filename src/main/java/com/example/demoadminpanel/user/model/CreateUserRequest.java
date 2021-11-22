package com.example.demoadminpanel.user.model;

import com.example.demoadminpanel.user.model.enums.UserRole;
import com.example.demoadminpanel.user.model.enums.UserStatus;
import lombok.Data;

@Data
public class CreateUserRequest {
    private String name;
    private String username;
    private String password;
    private String ipAddress;
    private String returnUrl;
    private UserRole role;
    private UserStatus status;
}
