package com.example.demoadminpanel.user.model;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String name;
    private String ipAddress;
    private String returnUrl;
}
