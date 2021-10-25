package com.example.demoadminpanel.security.authentication;

import com.example.demoadminpanel.security.authentication.AuthenticationService;
import com.example.demoadminpanel.user.model.enums.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Override
    public Authentication getLoggedInUsers() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public boolean hasAdminRights() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(grantedAuthority -> grantedAuthority
                                .getAuthority()
                                .equals(UserRole.ADMIN.name()));
    }
}
