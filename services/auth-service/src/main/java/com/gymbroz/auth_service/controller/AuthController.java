package com.gymbroz.auth_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gymbroz.auth_service.model.AuthUser;
import com.gymbroz.auth_service.service.AuthService;
import com.gymbroz.auth_service.service.ForgotPasswordService;
import com.gymbroz.auth_service.service.GoogleTokenVerifier;
import com.gymbroz.auth_service.service.ResetForgotPasswordService;
import com.gymbroz.auth_service.util.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private GoogleTokenVerifier googleIdTokenVerifier;

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @Autowired
    private ResetForgotPasswordService resetForgotPasswordService;

    @PostMapping("/register/email")
    public ResponseEntity<String> registerUserEmail(@RequestBody AuthUser user) throws Exception {
        try {
            ResponseEntity<String> response = authService.findUserByEmail(user.getEmail(), user.getRole());

            if (response.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User Alrady exists.");
            }

            authService.createUserEmail(user.getEmail(), user.getPassword(), user.getRole(), "email", "");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Registered User Seuccessfuly");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error registering user in database" + e.getMessage());
        }
    }

    @PostMapping("/login/email")
    public ResponseEntity<String> loginUserEmail(@RequestBody AuthUser user) {
        try {
            if (user.getEmail().isEmpty() || user.getRole().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request to auth service.");
            }
            // Step 1: Call the auth service to find the user by email
            ResponseEntity<String> response = authService.findUserByEmail(user.getEmail(), user.getRole());

            // Step 2: Handle the response from the auth service
            if (response == null || response.getBody() == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Auth service returned an invalid response.");
            }

            // Step 3: Check the status code of the response
            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            } else if (response.getStatusCode().is4xxClientError()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request to auth service.");
            } else if (response.getStatusCode().is5xxServerError()) {
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Auth service is unavailable.");
            } else if (!response.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Unexpected error from auth service: " + response.getStatusCode());
            }

            // Step 4: Parse the JSON response
            String responseBody = response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> jsonResponseMap;
            try {
                jsonResponseMap = objectMapper.readValue(responseBody, Map.class);
            } catch (JsonProcessingException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to parse auth service response: " + e.getMessage());
            }

            // Step 5: Extract fields from the JSON response
            String originalPassword = (String) jsonResponseMap.get("password");
            String originalEmail = (String) jsonResponseMap.get("email");
            String originalRole = (String) jsonResponseMap.get("role");

            // Step 6: Validate the extracted fields
            if (originalPassword == null || originalEmail == null || originalRole == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Auth service response is missing required fields.");
            }

            // Step 7: Verify the password
            if (!passwordEncoder.matches(user.getPassword(), originalPassword)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password.");
            }

            // Step 8: Generate and return the JWT token
            String token = jwtUtil.generateToken(originalEmail, originalRole);
            return ResponseEntity.ok(token);

        } catch (Exception e) {
            // Log the exception for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }

    @PostMapping("/google")
    public ResponseEntity<String> registerUserGoogle(@RequestBody AuthUser user)
            throws GeneralSecurityException, IOException {
        try {
            System.out.println(user.getGoogleId());
            GoogleIdToken.Payload payload = googleIdTokenVerifier.verify(user.getGoogleId());

            String email = payload.getEmail();
            String googleId = payload.getSubject();

            try {
                ResponseEntity<String> response = authService.findUserByEmail(email, user.getRole());

                if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                    authService.createUserEmail(email, user.getPassword(), user.getRole(), "google", googleId);
                }

            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error registering user in database" + e.getMessage());
            }

            String token = jwtUtil.generateToken(email, user.getRole());
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid Google ID token: " + e.getMessage());
        }
    }

    @PostMapping("/forgotPassword/{email}")
    public ResponseEntity<String> forgetPassword(@PathVariable String email) {
        try {
            forgotPasswordService.sendPasswordResetEmail(email);
            return ResponseEntity.ok("Password reset Email Sent Successfuly");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error sending password reset email: " + e.getMessage());
        }
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword){
         try {
           ResponseEntity<String> response = resetForgotPasswordService.restPassword(token, newPassword);
            return ResponseEntity.ok("Password reset: "+response.getStatusCode());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error resetting password: " + e.getMessage());
        }
    }

}
