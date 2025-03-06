package com.gymbroz.auth_service.service;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.gymbroz.auth_service.model.UserResponse;

@Service
public class CostumUserDetailsService implements UserDetailsService {
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch user from MongoDB
        ResponseEntity<UserResponse> userJsonResponse = restTemplate.getForEntity(
                "http://192.168.8.148:8080/api/db/gyms/fetchGymViaEmail/" + username, UserResponse.class);
        ResponseEntity<UserResponse> gymJsonResponse = restTemplate.getForEntity(
                "http://192.168.8.148:8080/api/db/gyms/fetchUserViaEmail/" + username, UserResponse.class);
        if (userJsonResponse.getStatusCode().is2xxSuccessful()) {
            UserResponse userResponse = userJsonResponse.getBody();
            return new org.springframework.security.core.userdetails.User(
                    userResponse.getUsername(),
                    userResponse.getPassword(),
                    userResponse.getAuthorities() // Convert roles to GrantedAuthority
            );

        } else if (gymJsonResponse.getStatusCode().is2xxSuccessful()) {
            UserResponse gymResponse = gymJsonResponse.getBody();
            return new org.springframework.security.core.userdetails.User(
                    gymResponse.getUsername(),
                    gymResponse.getPassword(),
                    gymResponse.getAuthorities() // Convert roles to GrantedAuthority
            );
        } else {
            throw new UsernameNotFoundException(username);
        }
        // Convert the User object to a Spring Security UserDetails object
    }
}
