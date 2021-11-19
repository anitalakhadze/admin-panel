package com.example.demoadminpanel.user.service;

import com.example.demoadminpanel.exception.customExceptions.ForbiddenRequestException;
import com.example.demoadminpanel.exception.customExceptions.ResourceAlreadyExistsException;
import com.example.demoadminpanel.exception.customExceptions.ResourceNotFoundException;
import com.example.demoadminpanel.user.model.*;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface UserService {
    Page<UserListResponse> getUsers(int page, int size);
    UserDetailedResponse getUser(Long id) throws ResourceNotFoundException;
    Long createUser(CreateUserRequest request) throws ResourceAlreadyExistsException;
    void updateUser(Long id, UpdateUserRequest request) throws ResourceNotFoundException;
    void deleteUser(Long id) throws ResourceNotFoundException;
    void updatePassword(String username, ChangePasswordBean changePasswordBean) throws ResourceNotFoundException, ForbiddenRequestException;
}
