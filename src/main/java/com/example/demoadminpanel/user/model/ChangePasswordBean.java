package com.example.demoadminpanel.user.model;

import lombok.Data;

@Data
public class ChangePasswordBean {
    private String oldPassword;
    private String newPassword;
}
