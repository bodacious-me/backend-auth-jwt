package com.gymbroz.auth_service.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.gymbroz.auth_service.model.PasswordResetToken;

@Component
@Service
public class AuthService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${db.server.address}")
    private String dbAddress;

    public ResponseEntity<String> createUserEmail(String email, String password, String role, String authMethod,
            String googleId) throws Exception {
        Map<String, Object> requesBody = new HashMap<>();
        requesBody.put("email", email);
        requesBody.put("password", passwordEncoder.encode(password));
        requesBody.put("authMethod", authMethod);
        requesBody.put("googleId", googleId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(requesBody, headers);

        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                    role.equals("gym") ? dbAddress + "/api/db/gyms"
                            : dbAddress + "/api/db/users",
                    requestEntity, String.class);
            return responseEntity;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error sending POST request: " + e.getMessage());
        }
    }

    public ResponseEntity<String> findUserByEmail(String email, String role) {
        // Validate role
        if (!"gym".equals(role) && !"user".equals(role)) {
            return ResponseEntity.badRequest().body("Invalid role specified");
        }

        String url = "gym".equals(role)
                ? dbAddress + "/api/db/gyms/fetchGymViaEmail/" + email
                : dbAddress + "/api/db/users/fetchUserViaEmail/" + email;

        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
            return responseEntity;
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body("Error fetching data: " + e.getResponseBodyAsString());
        } catch (ResourceAccessException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("DB service unavailable: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error: " + e.getMessage());
        }
    }

    public ResponseEntity<String> storeToken(PasswordResetToken passwordResetToken) {
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(dbAddress + "/api/db/tokens",
                    passwordResetToken, String.class);
            return response;
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body("Error fetching data: " + e);
        }
    }

    public ResponseEntity<String> fetchUserByToken(String token) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(dbAddress + "/api/db/tokens/" + token,
                    String.class);
                    return response;
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body("Error fetching user by token from DB: " + e.getMessage());
        }
    }


        public ResponseEntity<String> findUserById(String Id, String role) {
        // Validate role
        if (!"gym".equals(role) && !"user".equals(role)) {
            return ResponseEntity.badRequest().body("Invalid role specified");
        }

        String url = "gym".equals(role)
                ? dbAddress + "/api/db/gyms/" + Id
                : dbAddress + "/api/db/users/" + Id;

        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
            return responseEntity;
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body("Error fetching data: " + e.getResponseBodyAsString());
        } catch (ResourceAccessException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("DB service unavailable: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error: " + e.getMessage());
        }
    }
}
