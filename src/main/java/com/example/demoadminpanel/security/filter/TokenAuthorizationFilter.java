package com.example.demoadminpanel.security.filter;

import com.example.demoadminpanel.security.utils.TokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TokenAuthorizationFilter extends OncePerRequestFilter {
    private static final String TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (isPublicEndpoint(request) || hasValidToken(request)) {
            filterChain.doFilter(request, response);
        } else {
            handleInvalidToken(response);
        }
    }

    private boolean isPublicEndpoint(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        return servletPath.equals("/login") || servletPath.equals("/token/refresh");
    }

    private boolean hasValidToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        return authorizationHeader != null
               && authorizationHeader.startsWith(TOKEN_PREFIX)
               && isValidToken(authorizationHeader);
    }

    private boolean isValidToken(String authorizationHeader) {
        try {
            TokenUtils.validateToken(authorizationHeader);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    private void handleInvalidToken(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        Map<String, String> errors = new HashMap<>();
        errors.put("error_message", "Invalid or expired token");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), errors);
    }
}
