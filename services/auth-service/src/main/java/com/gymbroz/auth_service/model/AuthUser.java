package com.gymbroz.auth_service.model;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class AuthUser {
    private String email;
    private String password;
    private String googleId;
    private String role;
}
