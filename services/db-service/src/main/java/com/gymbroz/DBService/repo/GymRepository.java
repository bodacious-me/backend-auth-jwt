package com.gymbroz.DBService.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gymbroz.DBService.model.Gym;

public interface GymRepository extends MongoRepository<Gym, String> {
    List<Gym> findByEmail(String email);
    Optional<Gym> findById(String id);

}
