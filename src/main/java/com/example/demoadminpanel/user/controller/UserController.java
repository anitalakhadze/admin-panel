package com.example.demoadminpanel.user.controller;

import com.example.demoadminpanel.exception.customExceptions.ResourceAlreadyExistsException;
import com.example.demoadminpanel.exception.customExceptions.ResourceNotFoundException;
import com.example.demoadminpanel.user.model.CreateUserRequest;
import com.example.demoadminpanel.user.model.UpdateUserRequest;
import com.example.demoadminpanel.user.model.UserDetailedResponse;
import com.example.demoadminpanel.user.model.UserListResponse;
import com.example.demoadminpanel.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public Page<UserListResponse> getUsers(@RequestParam("page") int page, @RequestParam("size") int size) {
        return userService.getUsers(page, size);
    }

    @GetMapping("/user/{id}")
    public UserDetailedResponse getUser(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return userService.getUser(id);
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody CreateUserRequest request) throws ResourceAlreadyExistsException {
        return userService.createUser(request);
    }

    @PutMapping("/user/{id}")
    public void update (@PathVariable("id") Long id, @RequestBody UpdateUserRequest request) throws ResourceNotFoundException {
        userService.updateUser(id, request);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable("id") Long id) throws ResourceNotFoundException {
        userService.deleteUser(id);
    }
}
