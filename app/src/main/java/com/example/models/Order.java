package com.example.models;

public class Order {
    public int id;
    public int cart;
    public int user;
    public String description;
    public boolean is_completed;
    public int order_total;

    public Order(int id, int cart, int user, String description, boolean is_completed, int order_total) {
        this.id = id;
        this.cart = cart;
        this.user = user;
        this.description = description;
        this.is_completed = is_completed;
        this.order_total = order_total;
    }

    public Order() {
    }
}
