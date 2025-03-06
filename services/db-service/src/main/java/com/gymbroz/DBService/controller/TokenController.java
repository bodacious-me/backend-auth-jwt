package com.gymbroz.DBService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gymbroz.DBService.model.PasswordResetToken;

import com.gymbroz.DBService.repo.TokenRepo;
import com.gymbroz.DBService.service.TokenService;

@RestController
@RequestMapping("/api/db/tokens")
public class TokenController {

    @Autowired
    private TokenRepo tokenRepo;

    @Autowired
    private TokenService tokenService;

    @PostMapping("")
    public void storeToken(@RequestBody PasswordResetToken token) {
        tokenRepo.save(token);

    }

    @GetMapping("/{token}")
    public PasswordResetToken findToken(@PathVariable Long token) {

        try {
            PasswordResetToken resetToken = tokenRepo.findByToken(token);
             String role = tokenService.getUserRoleById(resetToken.getUserId());
             resetToken.setRole(role);
            return resetToken;
        } catch (RuntimeException e) {
            System.err.println("Error finding token: " + e.getMessage());
        }
        return null;
    }
}
