package com.gymbroz.DBService.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gymbroz.DBService.model.Gym;
import com.gymbroz.DBService.model.User;
import com.gymbroz.DBService.repo.GymRepository;
import com.gymbroz.DBService.repo.UserRepository;

@Service
public class TokenService {
    @Autowired
    private GymRepository gymRepo;
    @Autowired
    private UserRepository userRepo;

    public String getUserRoleById(String id) {
        Optional<Gym> gym = gymRepo.findById(id);
        Optional<User> user = userRepo.findById(id);

        if (gym.isPresent()) {
            return gym.get().getRole();
        } else if (user.isPresent()) {
            return user.get().getRole();
        } else {
            throw new RuntimeException("User not found");
        }
    }
}
