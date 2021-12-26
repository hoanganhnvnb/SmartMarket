package com.example.models;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Category implements Serializable {
    public int id;
    public String title;
    public String slug;
    public String description;
    public boolean active;
    public String image;
    public String test;

    public Category() {
        this.id = 0;
        this.title = "";
        this.slug = "";
        this.description = "";
        this.active = false;
        this.image = "";
        this.test = "";
    }

    public Category(int id, String title, String slug, String description, boolean active, String image) {
        this.id = id;
        this.title = title;
        this.slug = slug;
        this.description = description;
        this.active = active;
        this.image = image;
    }

    @NonNull
    @Override
    public String toString() {
        return this.title;
    }
}
