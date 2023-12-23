package com.example.demoadminpanel.user.service;

import com.example.demoadminpanel.user.entity.User;
import com.example.demoadminpanel.user.model.CreateUserRequest;
import com.example.demoadminpanel.user.model.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User fromCreateRequestToUser(CreateUserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setName(request.getName());
        user.setIpAddress(request.getIpAddress());
        user.setReturnUrl(request.getReturnUrl());
        user.setIsActive(request.getStatus().name().equals("ACTIVE"));
        user.setAddedAt(new Date());
        return  user;
    }

    public User fromUpdateRequestToUser(User user, UpdateUserRequest request) {
        user.setName(request.getName());
        user.setIpAddress(request.getIpAddress());
        user.setReturnUrl(request.getReturnUrl());
        user.setRole(request.getRole());
        return user;
    }

}
