package com.gymbroz.DBService.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.gymbroz.DBService.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>, CustomUserRepository{
    Optional<User> findByEmail(String email);
    Optional<User> findById(String id);
}