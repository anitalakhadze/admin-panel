package com.example.demoadminpanel.user.service.impl;

import com.example.demoadminpanel.email.EmailSender;
import com.example.demoadminpanel.exception.customExceptions.ResourceNotFoundException;
import com.example.demoadminpanel.user.entity.User;
import com.example.demoadminpanel.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
//    private final EmailSender emailSender;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException(String.format("User with username: %s not found", username));
        }

        User user = userOptional.get();
        List<SimpleGrantedAuthority> authorityList = Collections
                .singletonList(new SimpleGrantedAuthority(user.getRole().name()));

        // TODO: 12/24/23 not a necessary operation, just for testing purposes
//        emailSender.send("a.talakhadze@oppa.ge", "USER LOGIN", String.format("%s has logged in your app!", user.getUsername().toUpperCase()));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorityList
        );
    }
}
