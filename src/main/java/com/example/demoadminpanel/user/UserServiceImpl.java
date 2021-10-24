package com.example.demoadminpanel.user;

import com.example.demoadminpanel.exception.ResourceAlreadyExistsException;
import com.example.demoadminpanel.exception.ResourceNotFoundException;
import com.example.demoadminpanel.security.RevokeUserTokenEvent;
import com.example.demoadminpanel.user.models.CreateUserRequest;
import com.example.demoadminpanel.user.models.UpdateUserRequest;
import com.example.demoadminpanel.user.models.UserDetailedResponse;
import com.example.demoadminpanel.user.models.UserListResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
//    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserListResponse> getUsers(int page, int size) {
        log.info("Getting All Users");
        return userRepository
                .findAll(PageRequest.of(page, size))
                .map(UserListResponse::fromUser);
    }

    @Override
//    @PreAuthorize("hasRole('ADMIN')")
    public UserDetailedResponse getUser(Long id) {
        log.info("Getting user with id: {}", id);
        return userRepository
                .findById(id)
                .map(UserDetailedResponse::fromUser)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id: %s not found", id)));
    }

    @Override
//    @PreAuthorize("hasRole('ADMIN')")
    public Long createUser(CreateUserRequest request) {
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
//    @PreAuthorize("hasRole('ADMIN')")
    public void updateUser(Long id, UpdateUserRequest request) {
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
//    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(Long id) {
        log.info("Deleting user with id: {}", id);
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id: %s not found", id)));
        userRepository.delete(user);
        applicationEventPublisher.publishEvent(new RevokeUserTokenEvent(user.getUsername()));
    }
}
