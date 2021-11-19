package com.example.demoadminpanel.user.service.impl;

import com.example.demoadminpanel.exception.customExceptions.ForbiddenRequestException;
import com.example.demoadminpanel.exception.customExceptions.ResourceAlreadyExistsException;
import com.example.demoadminpanel.exception.customExceptions.ResourceNotFoundException;
import com.example.demoadminpanel.security.RevokeUserTokenEvent;
import com.example.demoadminpanel.user.entity.User;
import com.example.demoadminpanel.user.model.*;
import com.example.demoadminpanel.user.repository.UserRepository;
import com.example.demoadminpanel.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
//  @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Page<UserListResponse> getUsers(int page, int size) {
        log.info("Getting All Users");
        return userRepository
                .findAll(PageRequest.of(page, size))
                .map(UserListResponse::fromUser);
    }

    @Override
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserDetailedResponse getUser(Long id) throws ResourceNotFoundException {
        log.info("Getting user with id: {}", id);
        return userRepository
                .findById(id)
                .map(UserDetailedResponse::fromUser)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id: %s not found", id)));
    }

    @Override
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Long createUser(CreateUserRequest request) throws ResourceAlreadyExistsException {
        log.info("Creating User");
        Optional<User> userOptional = userRepository.findByUsername(request.getUsername());
        if (userOptional.isPresent()) {
            throw new ResourceAlreadyExistsException(String.format("User with username: %s already exists", request.getUsername()));
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setIpAddress(request.getIpAddress());
        user.setReturnUrl(request.getReturnUrl());
        userRepository.save(user);
        return user.getId();
    }

    @Override
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateUser(Long id, UpdateUserRequest request) throws ResourceNotFoundException {
        log.info("Updating user with id: {}", id);
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id: %s not found", id)));
        String password = request.getPassword();
        if (!StringUtils.isEmpty(password)) {
            user.setPassword(passwordEncoder.encode(password));
        }
        if (request.getRole() != null) {
            user.setRole(request.getRole());
        }
        user.setIpAddress(request.getIpAddress());
        user.setReturnUrl(request.getReturnUrl());
        userRepository.save(user);
        applicationEventPublisher.publishEvent(new RevokeUserTokenEvent(user.getUsername()));
    }

    @Override
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteUser(Long id) throws ResourceNotFoundException {
        log.info("Deleting user with id: {}", id);
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id: %s not found", id)));
        userRepository.delete(user);
        applicationEventPublisher.publishEvent(new RevokeUserTokenEvent(user.getUsername()));
    }

    @Override
    public void updatePassword(String username, ChangePasswordBean changePasswordBean) throws ResourceNotFoundException, ForbiddenRequestException {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with username: %s not found", username)));
        if (passwordEncoder.matches(changePasswordBean.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(changePasswordBean.getNewPassword()));
            userRepository.save(user);
        } else {
            throw new ForbiddenRequestException("Passwords do not match!");
        }
    }

}
