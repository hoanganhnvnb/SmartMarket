package com.example.models;

public class User {
    public int id;
    public String username;
    public String email;
    public String password;
    public String first_name;
    public String last_name;
    public String phone_number;
    public boolean is_superuser;

    public User(){
    }

    public User(String username, String email, String password, String first_name, String last_name) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public User(int id, String username, String email, String first_name, String last_name, boolean is_superuser, String phone_number) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.is_superuser = is_superuser;
        this.phone_number = phone_number;
    }


}



