package com.example.demoadminpanel.token.service;

import com.example.demoadminpanel.security.utils.TokenUtils;
import com.example.demoadminpanel.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class TokenService {

    private final UserRepository userRepository;

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                Map<String, String> tokens = TokenUtils.refreshToken(authorizationHeader, userRepository, request);
                handleSuccessResponse(response, tokens);
            } catch (Exception exception) {
                handleErrorResponse(response, exception);
            }
        } else {
            handleMissingRefreshToken();
        }
    }

    private void handleSuccessResponse(HttpServletResponse response, Map<String, String> tokens) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    private void handleErrorResponse(HttpServletResponse response, Exception exception) throws IOException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        Map<String, String> errors = new HashMap<>();
        errors.put("error_message", exception.getMessage());
        handleSuccessResponse(response, errors);
    }

    private void handleMissingRefreshToken() {
        log.error("Refresh token is missing");
        throw new RuntimeException("Refresh token is missing");
    }
}
