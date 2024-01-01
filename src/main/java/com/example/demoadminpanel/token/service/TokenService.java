package com.example.demoadminpanel.token.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface TokenService {
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
