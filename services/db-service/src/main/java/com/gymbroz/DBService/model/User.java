package com.gymbroz.DBService.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String first_name;
    private String last_name;
    @Indexed(unique = true) // Ensures the email is unique
    private String email;
    private String username;
    private String age;
    private String gender;
    private String weight;
    private String height;
    private String bio;
    private List<String> gymsIds;
    private String phoneNumber;
    private String password;
    private String authMethod;
    private String googleId;
    private boolean isFirstLogin;
    private String role;

    public User() {
    }

    public User(String first_name, String last_name, String email, String username, String age, String gender,
            String weight, String height, String bio, List<String> gymsIds, String phoneNumber, String password,
            String authMethod, String googleId, boolean isFirstLogin, String role) {
        this.age = age;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.username = username;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.bio = bio;
        this.gymsIds = gymsIds;
        this.authMethod = authMethod;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.googleId = googleId;
        this.isFirstLogin = isFirstLogin = true;
        this.role = role;
    }

           public void setRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }

        public void setIsFirstLogin(boolean isFirstLogin){
        this.isFirstLogin = isFirstLogin;
    }

    public boolean getIsFirstLogin(){
        return isFirstLogin;
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

    public String getId() {
        return id;
    }

    public List<String> getGymsIds() {
        return gymsIds;
    }

    public void setGymsIds(List<String> gymsIds) {
        this.gymsIds = gymsIds;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getWeight() {
        return weight;
    }

    public String getHeight() {
        return height;
    }

    public String getBio() {
        return bio;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

}
