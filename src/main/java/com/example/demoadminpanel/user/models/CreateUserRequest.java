package com.example.demoadminpanel.user.models;

import com.example.demoadminpanel.user.UserRole;
import lombok.Data;

@Data
public class CreateUserRequest {
    private String username;
    private String password;
    private String ipAddress;
    private String returnUrl;
    private UserRole role;
}
