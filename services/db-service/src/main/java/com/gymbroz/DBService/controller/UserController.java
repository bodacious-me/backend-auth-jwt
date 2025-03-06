package com.gymbroz.DBService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gymbroz.DBService.model.User;
import com.gymbroz.DBService.repo.UserRepository;

@RestController
@RequestMapping("/api/db/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("")
    public User createUser(@RequestBody User user) {
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
        throw new RuntimeException("User with this email already exists");
    }

        return userRepository.save(user);
    }

    @GetMapping("")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok().body(user))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/fetchUserViaEmail/{email}")
    public ResponseEntity<User> fetchUserViaEmail(@PathVariable String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);

    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User newUser) {
        return userRepository.findById(id).map(user -> {
            user.setAge(newUser.getAge());
            user.setBio(newUser.getBio());
            user.setEmail(newUser.getEmail());
            user.setFirstName(newUser.getFirstName());
            user.setGender(newUser.getGender());
            user.setHeight(newUser.getHeight());
            user.setId(newUser.getId());
            user.setLastName(newUser.getLastName());
            user.setUsername(newUser.getUsername());
            user.setWeight(newUser.getWeight());
            user.setPassword(newUser.getPassword());
            User updatedUser = userRepository.save(user);
            return ResponseEntity.ok().body(updatedUser);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}