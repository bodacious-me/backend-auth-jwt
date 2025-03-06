package com.gymbroz.DBService.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.gymbroz.DBService.model.PasswordResetToken;


@Repository
@Component
public interface TokenRepo extends MongoRepository<PasswordResetToken, String> {
  PasswordResetToken findByToken(Long token);

}
