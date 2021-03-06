package com.example.demoadminpanel.security.authentication;

import org.springframework.security.core.Authentication;

public interface AuthenticationService {
    Authentication getLoggedInUsers();
    boolean hasAdminRights();
}
