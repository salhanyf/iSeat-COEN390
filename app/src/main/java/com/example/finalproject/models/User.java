/*
    File:           User.java
    Authors:        Adnan Saab          #40075504
                    Samson Kaller       #40136815
                    Farah Salhany       #40074803
                    Shahin Khalkhali    #40057384
                    Shayan Khalkhali    #40059491
                    Marwan Al-Ghaziri   #40126554
    Description:    This class provides the User model class that is used to hold data on a user
                    throughout the app. Data attributes match the User nodes on Firebase.
*/
package com.example.finalproject.models;

public class User {
    private String username;
    private String email;
    private boolean isAdmin;
    private String key;
    private String dateCreated;

    public User() {} // empty constructor for Firebase class binding

    // parameterized constructor
    public User(String username, String email, boolean isAdmin, String dateCreated, String key) {
        // set all data
        this.username = username;
        this.email = email;
        this.isAdmin = isAdmin;
        this.dateCreated = dateCreated;
        this.key = key;
    }

    // getters
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public boolean getIsAdmin() { return isAdmin; }
    public String getKey() { return key; }
    public String getDateCreated() { return dateCreated; }

    // setters
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setIsAdmin(boolean isAdmin) { this.isAdmin = isAdmin; }
    public void setKey(String key) { this.key = key; }
    public void setDateCreated(String dateCreated) { this.dateCreated = dateCreated; }
}
