package com.example.demoadminpanel.token.controller;

import com.example.demoadminpanel.token.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@AllArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        tokenService.refreshToken(request, response);
    }

}