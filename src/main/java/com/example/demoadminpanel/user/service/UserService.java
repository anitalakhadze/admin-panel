package com.example.demoadminpanel.user.service;

import com.example.demoadminpanel.exception.customExceptions.GeneralApiException;
import com.example.demoadminpanel.exception.customExceptions.ResourceAlreadyExistsException;
import com.example.demoadminpanel.exception.customExceptions.ResourceNotFoundException;
import com.example.demoadminpanel.user.model.*;

import java.util.List;

public interface UserService {
    List<UserListResponse> getUsers();
    UserDetailedResponse getUser(Long id) throws ResourceNotFoundException;
    Long createUser(CreateUserRequest request) throws ResourceAlreadyExistsException;
    void updateUser(Long id, UpdateUserRequest request) throws ResourceNotFoundException;
    void deleteUser(Long id) throws ResourceNotFoundException;
    void updatePassword(String username, ChangePasswordBean changePasswordBean) throws ResourceNotFoundException, GeneralApiException;
}
