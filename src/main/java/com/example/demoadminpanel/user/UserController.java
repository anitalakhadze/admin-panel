package com.example.demoadminpanel.user;

import com.example.demoadminpanel.user.models.CreateUserRequest;
import com.example.demoadminpanel.user.models.UpdateUserRequest;
import com.example.demoadminpanel.user.models.UserDetailedResponse;
import com.example.demoadminpanel.user.models.UserListResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public Page<UserListResponse> getUsers(@RequestParam("page") int page,
                                              @RequestParam("size") int size) {
        return userService.getUsers(page, size);
    }

    @GetMapping("/user/{id}")
    public UserDetailedResponse getUser(@PathVariable("id") Long id) {
        return userService.getUser(id);
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    @PutMapping("/user/{id}")
    public void update (@PathVariable("id") Long id,
                        @RequestBody UpdateUserRequest request) {
        userService.updateUser(id, request);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }
}
