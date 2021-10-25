package com.example.demoadminpanel.user.model;

import com.example.demoadminpanel.user.model.enums.UserRole;
import lombok.Data;

@Data
public class CreateUserRequest {
    private String username;
    private String password;
    private String ipAddress;
    private String returnUrl;
    private UserRole role;
}
