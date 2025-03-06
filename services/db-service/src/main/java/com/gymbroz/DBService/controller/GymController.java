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

import com.gymbroz.DBService.model.Gym;
import com.gymbroz.DBService.repo.GymRepository;

@RestController
@RequestMapping("/api/db/gyms")
public class GymController {
        @Autowired
    private GymRepository gymRepository;

    @PostMapping("")
    public Gym createGym(@RequestBody Gym gym) {
        return gymRepository.save(gym);
    }

    @GetMapping("")
    public List<Gym> getAllGyms() {
        return gymRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gym> getGymById(@PathVariable String id) {
        return gymRepository.findById(id)
                .map(user -> ResponseEntity.ok().body(user))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/fetchGymViaEmail/{email}")
    public ResponseEntity<Gym> fetchGymViaEmail(@PathVariable String email) {
        final List<Gym> gyms = gymRepository.findByEmail(email);
        if (gyms.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(gyms.get(0));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Gym> updateGym(@PathVariable String id, @RequestBody Gym newGym) {
        return gymRepository.findById(id).map(gym -> {
            gym.setAbout(newGym.getAbout());
            gym.setCity(newGym.getCity());
            gym.setCountry(newGym.getCountry());
            gym.setEmail(newGym.getEmail());
            gym.setGymName(newGym.getGymName());
            gym.setMembersIds(newGym.getMembersIds());
            gym.setOwnerName(newGym.getOwnerName());
            gym.setTrainersNames(newGym.getTrainersNames());
            gym.setUsername(newGym.getUsername());
            Gym updatedGym = gymRepository.save(gym);
            return ResponseEntity.ok().body(updatedGym);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGym(@PathVariable String id) {
        return gymRepository.findById(id)
                .map(gym -> {
                    gymRepository.delete(gym);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
