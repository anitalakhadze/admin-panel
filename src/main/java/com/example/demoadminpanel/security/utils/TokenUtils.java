package com.example.demoadminpanel.security.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demoadminpanel.exception.customExceptions.ResourceNotFoundException;
import com.example.demoadminpanel.user.entity.User;
import com.example.demoadminpanel.user.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class TokenUtils {
    private static final long ACCESS_TOKEN_EXPIRATION_PERIOD = Duration.ofDays(10).toMillis();
    private static final long REFRESH_TOKEN_EXPIRATION_PERIOD = Duration.ofDays(30).toMillis();
    private static final String ACCESS_TOKEN_HEADER_KEY = "access_token";
    private static final String REFRESH_TOKEN_HEADER_KEY = "refresh_token";
    private static final String SECRET_KEY = "someSecretKey";
    private static final String CLAIM_KEY = "roles";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    public static Map<String, String> getTokenHeaders(String username, String issuer, Collection<GrantedAuthority> authorities) {
        Map<String, String> tokens = new HashMap<>();
        tokens.put(ACCESS_TOKEN_HEADER_KEY, getAccessToken(username, issuer, authorities));
        tokens.put(REFRESH_TOKEN_HEADER_KEY, getRefreshToken(username, issuer));
        return tokens;
    }

    public static void validateToken(String authorizationHeader) {
        String token = authorizationHeader.substring(TOKEN_PREFIX.length());
        JWTVerifier verifier = JWT.require(ALGORITHM).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String username = decodedJWT.getSubject();
        String[] roles = decodedJWT.getClaim(CLAIM_KEY).asArray(String.class);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Arrays.stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    public static Map<String, String> refreshToken(String authorizationHeader, UserRepository userRepository, HttpServletRequest request) throws ResourceNotFoundException {
        String refreshToken = authorizationHeader.substring(TOKEN_PREFIX.length());
        JWTVerifier verifier = JWT.require(ALGORITHM).build();
        DecodedJWT decodedJWT = verifier.verify(refreshToken);
        String username = decodedJWT.getSubject();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User cannot be found"));
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
        String authenticationToken = getAccessToken(username, request.getRequestURL().toString(), authorities);
        Map<String, String> tokens = new HashMap<>();
        tokens.put(ACCESS_TOKEN_HEADER_KEY, authenticationToken);
        tokens.put(REFRESH_TOKEN_HEADER_KEY, refreshToken);
        return tokens;
    }

    private static String getAccessToken(String username, String issuer, Collection<GrantedAuthority> authorities) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_PERIOD))
                .withIssuer(issuer)
                .withClaim(CLAIM_KEY, authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(ALGORITHM);
    }

    private static String getRefreshToken(String username, String issuer) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_PERIOD))
                .withIssuer(issuer)
                .sign(ALGORITHM);
    }
}
