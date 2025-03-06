package com.gymbroz.DBService.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "tokens")
public class PasswordResetToken {

    @Id
    private String id;
    private Long token;
    private String userId;
    private Date expireDate;
    private String role;

}
