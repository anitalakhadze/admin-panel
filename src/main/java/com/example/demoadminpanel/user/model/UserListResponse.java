package com.example.demoadminpanel.user.model;

import com.example.demoadminpanel.user.entity.User;
import com.example.demoadminpanel.user.model.enums.UserRole;
import lombok.*;

import java.util.Date;

@Data
public class UserListResponse {
    private Long id;
    private String name;
    private String ipAddress;
    private String returnUrl;
    private String isActive;
    private Date addedAt;
    private UserRole userRole;

    public static UserListResponse fromUser(User user) {
        UserListResponse userListResponse = new UserDetailedResponse();
        userListResponse.setId(user.getId());
        userListResponse.setName(user.getName());
        userListResponse.setIpAddress(user.getIpAddress());
        userListResponse.setReturnUrl(user.getReturnUrl());
        userListResponse.setIsActive(user.getIsActive() ? "აქტიური" : "არააქტიური");
        userListResponse.setAddedAt(user.getAddedAt());
        userListResponse.setUserRole(user.getRole());
        return userListResponse;
    }
}
