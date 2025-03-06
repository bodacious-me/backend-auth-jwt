package com.gymbroz.auth_service.model;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class PasswordResetToken {

    private String id;
    private Long token;
    private String userId;
    private Date expireDate;
    private String role;

}



