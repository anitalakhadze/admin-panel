package com.example.demoadminpanel.user.service.impl;

import com.example.demoadminpanel.exception.customExceptions.ForbiddenRequestException;
import com.example.demoadminpanel.exception.customExceptions.GeneralApiException;
import com.example.demoadminpanel.exception.customExceptions.ResourceAlreadyExistsException;
import com.example.demoadminpanel.exception.customExceptions.ResourceNotFoundException;
import com.example.demoadminpanel.security.RevokeUserTokenEvent;
import com.example.demoadminpanel.user.entity.User;
import com.example.demoadminpanel.user.model.*;
import com.example.demoadminpanel.user.repository.UserRepository;
import com.example.demoadminpanel.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public List<UserListResponse> getUsers() {
        log.info("Getting All Users");
        return userRepository
                .findAll()
                .stream()
                .map(UserListResponse::fromUser)
                .collect(Collectors.toList());
    }

    @Override
    public UserDetailedResponse getUser(Long id) throws ResourceNotFoundException {
        log.info("Getting user with id: {}", id);
        return userRepository
                .findById(id)
                .map(UserDetailedResponse::fromUser)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id: %s not found", id)));
    }

    @Override
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
        user.setName(request.getName());
        user.setIpAddress(request.getIpAddress());
        user.setReturnUrl(request.getReturnUrl());
        user.setIsActive(request.getStatus().name().equals("ACTIVE"));
        user.setAddedAt(new Date());
        userRepository.save(user);
        return user.getId();
    }

    @Override
    public void updateUser(Long id, UpdateUserRequest request) throws ResourceNotFoundException {
        log.info("Updating user with id: {}", id);
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id: %s not found", id)));
        user.setName(request.getName());
        user.setIpAddress(request.getIpAddress());
        user.setReturnUrl(request.getReturnUrl());
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) throws ResourceNotFoundException {
        log.info("Deleting user with id: {}", id);
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id: %s not found", id)));
        userRepository.delete(user);
        applicationEventPublisher.publishEvent(new RevokeUserTokenEvent(user.getUsername()));
    }

    @Override
    public void updatePassword(String username, ChangePasswordBean bean) throws ResourceNotFoundException, GeneralApiException {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with username: %s not found", username)));
        if (!passwordEncoder.matches(bean.getOldPassword(), user.getPassword())) {
            throw new GeneralApiException("თქვენ მიერ შეყვანილი პაროლი არასწორია. გთხოვთ, სცადოთ კიდევ ერთხელ.");
        } else if (!bean.getNewPassword().equals(bean.getNewPasswordDub())) {
            throw new GeneralApiException("ახალი და განმეორებით შეყვანილი პაროლები არ ემთხვევა ერთმანეთს");
        } else {
            user.setPassword(passwordEncoder.encode(bean.getNewPassword()));
            userRepository.save(user);
        }
    }
}
