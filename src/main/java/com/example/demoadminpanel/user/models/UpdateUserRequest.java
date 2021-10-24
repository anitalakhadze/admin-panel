package com.example.demoadminpanel.user.models;

import com.example.demoadminpanel.user.UserRole;
import lombok.Data;

@Data
public class UpdateUserRequest {
    private String password;
    private String ipAddress;
    private String returnUrl;
    private UserRole role;
}
