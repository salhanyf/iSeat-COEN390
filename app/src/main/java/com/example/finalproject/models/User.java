package com.example.finalproject.models;

public class User {
    private String username;
    private String email;
    private boolean isAdmin;
    private String key;

    public User() {
    }

    public User(String username, String email, boolean isAdmin, String key) {
        this.username = username;
        this.email = email;
        this.isAdmin = isAdmin;
        this.key = key;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
