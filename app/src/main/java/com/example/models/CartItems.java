package com.example.models;

import java.io.Serializable;

public class CartItems implements Serializable {
    public int id;
    public int cart;
    public Items items;
    public int quantity;

    public CartItems(int id, int cart, Items items, int quantity) {
        this.id = id;
        this.cart = cart;
        this.items = items;
        this.quantity = quantity;
    }

    public CartItems() {
    }
}
