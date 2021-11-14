package com.example.models;

public class Category {
    public int id;
    public String slug;
    public String description;
    public boolean active;

    public Category() {
        this.id = 0;
        this.slug = "";
        this.description = "";
        this.active = false;
    }

    public Category(int id, String slug, String description, boolean active) {
        this.id = id;
        this.slug = slug;
        this.description = description;
        this.active = active;
    }
}
