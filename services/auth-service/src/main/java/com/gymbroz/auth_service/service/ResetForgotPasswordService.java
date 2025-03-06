package com.gymbroz.auth_service.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ResetForgotPasswordService {

    @Value("${db.server.address}")
    private String dbAddress;

    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<String> restPassword(String toke, String newPassword)
            throws JsonMappingException, JsonProcessingException, ParseException {
        ResponseEntity<String> fetchUserResponse = authService.fetchUserByToken(toke);
        if (fetchUserResponse.getStatusCode().is2xxSuccessful()) {
            String responseString = fetchUserResponse.getBody();
            Map<String, Object> responseMap = objectMapper.readValue(responseString, Map.class);
            String userId = responseMap.get("userId").toString();
            String userRole = responseMap.get("role").toString();
            String url = "gym".equals(userRole)
                    ? dbAddress + "/api/db/gyms/" + userId
                    : dbAddress + "/api/db/users/" + userId;
//
            System.out.println("URL:   " + url);
//
            Object dateValue = responseMap.get("expireDate");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            Date expireDate = dateFormat.parse((String) dateValue);
            if (expireDate.before(new Date())) {
                throw new RuntimeException("Token has Expired");
            } else {
                // do a put request to update the user now that tyou have tthe user role
                String responseBody = authService.findUserById(userId, userRole).getBody();
                Map<String, Object> userResponseMap = objectMapper.readValue(responseBody, Map.class);
                userResponseMap.put("password", passwordEncoder.encode(newPassword));

                HttpHeaders headers = new HttpHeaders();
                headers.set("Content-Type", "application/json");
                HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(userResponseMap, headers);
                return restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
            }

        } else {
            throw new RuntimeException("Invalid Token");
        }
    }
}
