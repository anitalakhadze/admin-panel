package com.example.demoadminpanel.user.model;

import com.example.demoadminpanel.user.entity.User;
import com.example.demoadminpanel.user.model.enums.UserRole;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserDetailedResponse extends UserListResponse{
    private UserRole role;

    public static UserDetailedResponse fromUser(User user) {
        UserDetailedResponse userDetailedResponse = new UserDetailedResponse();
        userDetailedResponse.setId(user.getId());
        userDetailedResponse.setUsername(user.getUsername());
        userDetailedResponse.setIpAddress(user.getIpAddress());
        userDetailedResponse.setReturnUrl(user.getReturnUrl());
        userDetailedResponse.setRole(user.getRole());
        return userDetailedResponse;
    }
}
