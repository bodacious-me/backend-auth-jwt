package com.gymbroz.auth_service.service;

import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gymbroz.auth_service.model.PasswordResetToken;

@Service
public class ForgotPasswordService {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private AuthService authService;

    @Autowired
    PasswordResetToken passwordResetToken;

    public void sendPasswordResetEmail(String userEmail) throws JsonProcessingException {
        String responseBody;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ResponseEntity<String> gymResponse = authService.findUserByEmail(userEmail, "gym");
            ResponseEntity<String> userResponse = authService.findUserByEmail(userEmail, "user");

            if (gymResponse.getStatusCode().is2xxSuccessful()) {
                responseBody = gymResponse.getBody();
            } else if (userResponse.getStatusCode().is2xxSuccessful()) {
                responseBody = userResponse.getBody();
            } else {
                throw new RuntimeException("User not found in both gym and user databases.");
            }

            Map<String, Object> user = objectMapper.readValue(responseBody, Map.class);
            Long token = generateRandomNumber();
            passwordResetToken.setToken(token);
            passwordResetToken.setUserId(user.get("id").toString());
            passwordResetToken.setExpireDate(calculateExpiryDate());
            authService.storeToken(passwordResetToken);

            sendEmail(userEmail, token.toString());
        } catch (HttpClientErrorException e) {
            System.err.println("HTTP error while contacting AuthService: " + e.getMessage());
            throw new RuntimeException("Failed to contact AuthService", e);
        } catch (JsonProcessingException e) {
            System.err.println("Error parsing JSON response: " + e.getMessage());
            throw new RuntimeException("Failed to parse user data", e);
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            throw new RuntimeException("An unexpected error occurred", e);
        }
    }
    // generate Random Long

    private Long generateRandomNumber() {
        Random random = new Random();
        return 100000L + random.nextInt(900000); // Generates a 6-digit number
    }

    // calculate expire date
    private Date calculateExpiryDate() {
        long expireTimeInMiliSec = 10 * 60 * 1000;
        return new Date(System.currentTimeMillis() + expireTimeInMiliSec);
    }

    public void sendEmail(String email, String token) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(email);
            mailMessage.setSubject("Password reset request");
            mailMessage.setText("Your password reset token is: " + token + "\nThis token is valid for 10 minutes.");
            mailSender.send(mailMessage);
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
            throw new RuntimeException("Failed to send password reset email", e);
        }
    }

}
