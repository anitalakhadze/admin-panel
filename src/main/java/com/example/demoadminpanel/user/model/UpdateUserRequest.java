package com.example.demoadminpanel.user.model;

import com.example.demoadminpanel.user.model.enums.UserRole;
import lombok.Data;

@Data
public class UpdateUserRequest {
    private String password;
    private String ipAddress;
    private String returnUrl;
    private UserRole role;
}
