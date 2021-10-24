package com.example.demoadminpanel.user.models;

import com.example.demoadminpanel.user.User;
import lombok.*;

@Data
public class UserListResponse {
    private Long id;
    private String username;
    private String ipAddress;
    private String returnUrl;

    public static UserListResponse fromUser(User user) {
        UserListResponse userListResponse = new UserDetailedResponse();
        userListResponse.setId(user.getId());
        userListResponse.setUsername(userListResponse.getUsername());
        userListResponse.setIpAddress(user.getIpAddress());
        userListResponse.setReturnUrl(user.getReturnUrl());
        return userListResponse;
    }
}
