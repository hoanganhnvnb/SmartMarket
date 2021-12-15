package com.example.models;

public class Token {
    public String access;
    public String refresh;

    public Token(){}

    public Token(String access, String refresh) {
        this.access = access;
        this.refresh = refresh;
    }
}
