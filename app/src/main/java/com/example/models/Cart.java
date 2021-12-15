package com.example.models;

public class Cart {
    public int id;
    public int user;
    public String create_at;
    public String update_at;
    public boolean active;

    public Cart() {
    }

    public Cart(int id, int user, String create_at, String update_at, boolean active) {
        this.id = id;
        this.user = user;
        this.create_at = create_at;
        this.update_at = update_at;
        this.active = active;
    }
}

