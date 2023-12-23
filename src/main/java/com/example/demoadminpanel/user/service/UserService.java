package com.example.demoadminpanel.user.service;

import com.example.demoadminpanel.exception.customExceptions.GeneralApiException;
import com.example.demoadminpanel.exception.customExceptions.ResourceAlreadyExistsException;
import com.example.demoadminpanel.exception.customExceptions.ResourceNotFoundException;
import com.example.demoadminpanel.security.RevokeUserTokenEvent;
import com.example.demoadminpanel.user.entity.User;
import com.example.demoadminpanel.user.model.*;
import com.example.demoadminpanel.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    public List<UserListResponse> getUsers() {
        log.debug("Getting All Users");
        return userRepository
                .findAll()
                .stream()
                .map(UserListResponse::fromUser)
                .collect(Collectors.toList());
    }

    public UserDetailedResponse getUser(Long id) {
        log.debug("Getting user with id: {}", id);
        return userRepository
                .findById(id)
                .map(UserDetailedResponse::fromUser)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id: %s not found", id)));
    }

    @Transactional
    public Long createUser(CreateUserRequest request) {
        log.debug("Creating user with username: {}", request.getUsername());

        Optional<User> userOptional = userRepository.findByUsername(request.getUsername());
        if (userOptional.isPresent()) {
            log.error("User with username: {} already exists", request.getUsername());
            throw new ResourceAlreadyExistsException(String.format("User with username: %s already exists", request.getUsername()));
        }

        User user = userRepository.save(userMapper.fromCreateRequestToUser(request));

        return user.getId();
    }

    @Transactional
    public void updateUser(Long id, UpdateUserRequest request) {
        log.debug("Updating user with id: {}", id);

        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id: %s not found", id)));

        userRepository.save(userMapper.fromUpdateRequestToUser(user, request));
    }

    @Transactional
    public void deleteUser(Long id) {
        log.debug("Deleting user with id: {}", id);

        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id: %s not found", id)));

        userRepository.delete(user);

        applicationEventPublisher.publishEvent(new RevokeUserTokenEvent(user.getUsername()));
    }

    @Transactional
    public void updatePassword(String username, ChangePasswordBean bean) {
        log.debug("Updating password for user with username: {}", username);

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with username: %s not found", username)));

        if (!passwordEncoder.matches(bean.getOldPassword(), user.getPassword())) {
            log.error("Old password is incorrect.");
            throw new GeneralApiException("Old password is incorrect.");
        }

        if (!bean.getNewPassword().equals(bean.getNewPasswordDub())) {
            log.error("New and repeated passwords do not match.");
            throw new GeneralApiException("New and repeated passwords do not match.");
        }

        user.setPassword(passwordEncoder.encode(bean.getNewPassword()));
        userRepository.save(user);
    }
}
