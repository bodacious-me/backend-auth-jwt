package com.gymbroz.auth_service.model;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class AuthResponse {
   private String email;
   private String password;
   private String role; 
}
