package com.example.demoadminpanel.user;

import com.example.demoadminpanel.user.models.CreateUserRequest;
import com.example.demoadminpanel.user.models.UpdateUserRequest;
import com.example.demoadminpanel.user.models.UserDetailedResponse;
import com.example.demoadminpanel.user.models.UserListResponse;
import org.springframework.data.domain.Page;

public interface UserService {
    Page<UserListResponse> getUsers(int page, int size);
    UserDetailedResponse getUser(Long id);
    Long createUser(CreateUserRequest request);
    void updateUser(Long id, UpdateUserRequest request);
    void deleteUser(Long id);
}
