package com.example.models;

public class Notify {
    public int id;
    public String title;
    public String content;
    public int user;
    public String create_at;
    public Boolean is_read;


    public Notify(int id, String title, String content, int user, String create_at, Boolean is_read) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.create_at = create_at;
        this.is_read = is_read;
    }

    public Notify() {
    }
}
