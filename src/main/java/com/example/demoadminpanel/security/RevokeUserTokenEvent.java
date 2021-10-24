package com.example.demoadminpanel.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public class RevokeUserTokenEvent {
    String username;
}
