package com.example.demoadminpanel.user.service;

import com.example.demoadminpanel.exception.customExceptions.ResourceAlreadyExistsException;
import com.example.demoadminpanel.exception.customExceptions.ResourceNotFoundException;
import com.example.demoadminpanel.user.model.CreateUserRequest;
import com.example.demoadminpanel.user.model.UpdateUserRequest;
import com.example.demoadminpanel.user.model.UserDetailedResponse;
import com.example.demoadminpanel.user.model.UserListResponse;
import org.springframework.data.domain.Page;

public interface UserService {
    Page<UserListResponse> getUsers(int page, int size);
    UserDetailedResponse getUser(Long id) throws ResourceNotFoundException;
    Long createUser(CreateUserRequest request) throws ResourceAlreadyExistsException;
    void updateUser(Long id, UpdateUserRequest request) throws ResourceNotFoundException;
    void deleteUser(Long id) throws ResourceNotFoundException;
}
