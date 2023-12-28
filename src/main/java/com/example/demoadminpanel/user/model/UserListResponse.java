package com.example.demoadminpanel.user.model;

import com.example.demoadminpanel.user.entity.User;
import com.example.demoadminpanel.user.model.enums.UserRole;
import com.example.demoadminpanel.user.model.enums.UserStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserListResponse {
    private Long id;
    private String name;
    private String ipAddress;
    private String returnUrl;
    private String isActive;
    private LocalDateTime registeredAt;
    private UserRole userRole;

    public static UserListResponse fromUser(User user) {
        UserListResponse userListResponse = new UserDetailedResponse();
        userListResponse.setId(user.getId());
        userListResponse.setName(user.getName());
        userListResponse.setIpAddress(user.getIpAddress());
        userListResponse.setReturnUrl(user.getReturnUrl());
        userListResponse.setIsActive(user.getStatus().equals(UserStatus.ACTIVE) ? "Active" : "Inactive");
        userListResponse.setRegisteredAt(user.getRegisteredAt());
        userListResponse.setUserRole(user.getRole());
        return userListResponse;
    }
}
