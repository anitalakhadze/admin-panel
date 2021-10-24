package com.example.demoadminpanel.user;

import com.example.demoadminpanel.email.EmailSender;
import com.example.demoadminpanel.exception.ApiExceptionHandler;
import com.example.demoadminpanel.exception.customExceptions.ResourceNotFoundException;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService, UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final EmailSender emailSender;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) {
            throw new ResourceNotFoundException(String.format("User with username: %s not found", username));
        }
        User user = userOptional.get();
        List<SimpleGrantedAuthority> authorityList = Collections
                .singletonList(new SimpleGrantedAuthority(user.getRole().name()));
        emailSender.send("atala15@freeuni.edu.ge", "user login", "User has logged in");
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorityList
        );
    }

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
            throw new ApiExceptionHandler.ResourceAlreadyExistsException(String.format("User with username: %s already exists", request.getUsername()));
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
