package com.example.demoadminpanel.user.controller;

import com.example.demoadminpanel.excel.ExcelService;
import com.example.demoadminpanel.exception.customExceptions.GeneralApiException;
import com.example.demoadminpanel.exception.customExceptions.ResourceAlreadyExistsException;
import com.example.demoadminpanel.exception.customExceptions.ResourceNotFoundException;
import com.example.demoadminpanel.user.model.*;
import com.example.demoadminpanel.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ExcelService excelService;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<UserListResponse> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public UserDetailedResponse getUser(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return userService.getUser(id);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Long create( @Valid @RequestBody CreateUserRequest request) throws ResourceAlreadyExistsException {
        return userService.createUser(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void update (@PathVariable("id") Long id, @Valid @RequestBody UpdateUserRequest request) throws ResourceNotFoundException {
        userService.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable("id") Long id) throws ResourceNotFoundException {
        userService.deleteUser(id);
    }

    @PutMapping("/password/{username}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public void updatePassword(@PathVariable("username") String username, @Valid @RequestBody ChangePasswordBean changePasswordBean) throws ResourceNotFoundException, GeneralApiException {
        userService.updatePassword(username, changePasswordBean);
    }

    @PostMapping(value = "/excel", produces = "application/csv")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void generateExcelOfUsers(HttpServletResponse response) throws IOException {
        excelService.generateExcelOfUsers(response);
    }
}
