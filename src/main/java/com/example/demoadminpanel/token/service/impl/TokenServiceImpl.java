package com.example.demoadminpanel.token.service.impl;

import com.example.demoadminpanel.security.utils.TokenUtils;
import com.example.demoadminpanel.token.service.TokenService;
import com.example.demoadminpanel.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final UserRepository userRepository;

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                Map<String, String> tokens = TokenUtils.refreshToken(authorizationHeader, userRepository, request);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception exception) {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                Map<String, String> errors = new HashMap<>();
                errors.put("error_message", exception.getMessage());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), errors);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
}
