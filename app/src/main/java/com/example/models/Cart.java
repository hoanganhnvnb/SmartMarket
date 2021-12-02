package com.example.models;

public class Cart {
    public int id;
    public String name;
    public long price;
    public String picture;
    public int quantity;

    public Cart(int id, String name, long price, String picture, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.picture = picture;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

