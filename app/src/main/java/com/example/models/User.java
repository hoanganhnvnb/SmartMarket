package com.example.models;

public class User {
    public String username;
    public String email;
    public String first_name;
    public String last_name;


    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }

}
