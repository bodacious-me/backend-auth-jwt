package com.gymbroz.DBService.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "gyms")
public class Gym {

    @Id
    private String id;
    private String gymName;
    @Indexed(unique = true) // Ensures the email is unique
    private String email;
    private String username;
    private String about;
    private String country;
    private String city;
    private List<String> membersIds;
    private String ownerName;
    private List<String> trainersNames;
    private String phoneNumber;
    private String authMethod;
    private String password;
    private String googleId;
    private boolean isFirstLogin = true;
    private String role = "gym";

    public Gym(String gymName, String email, String username, String about, String country, String city,
            List<String> membersIds, String ownerName, List<String> trainersNames, String phoneNumber,
            String authMethod, String password, String googleId, boolean isFirstLogin, String role) {
        this.about = about;
        this.city = city;
        this.country = country;
        this.email = email;
        this.gymName = gymName;
        this.membersIds = membersIds;
        this.trainersNames = trainersNames;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.authMethod = authMethod;
        this.password = password;
        this.googleId = googleId;
        this.isFirstLogin = isFirstLogin;
        this.role = role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setIsFirstLogin(boolean isFirstLogin) {
        this.isFirstLogin = isFirstLogin;
    }

    public boolean getIsFirstLogin() {
        return isFirstLogin;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setAuthMethod(String authMethod) {
        this.authMethod = authMethod;
    }

    public String getAuthMethod() {
        return authMethod;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getGymName() {
        return gymName;
    }

    public void setGymName(String gymName) {
        this.gymName = gymName;
    }

    // Getter and setter for email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and setter for username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getter and setter for about
    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    // Getter and setter for country
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    // Getter and setter for city
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    // Getter and setter for membersIds
    public List<String> getMembersIds() {
        return membersIds;
    }

    public void setMembersIds(List<String> membersIds) {
        this.membersIds = membersIds;
    }

    // Getter and setter for ownerName
    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    // Getter and setter for trainers
    public List<String> getTrainersNames() {
        return trainersNames;
    }

    public void setTrainersNames(List<String> trainers) {
        this.trainersNames = trainers;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}
