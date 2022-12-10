/*
    File:           Room.java
    Authors:        Adnan Saab          #40075504
                    Samson Kaller       #40136815
                    Farah Salhany       #40074803
                    Shahin Khalkhali    #40057384
                    Shayan Khalkhali    #40059491
                    Marwan Al-Ghaziri   #40126554
    Description:    This class provides the Room model class that is used to hold data on a room
                    throughout the app. Data attributes match the Room nodes on Firebase.
*/
package com.example.finalproject.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Room {
    private String key;
    private String name;
    private String location;
    private String capacity;
    private String admin;

    public Room() {} // empty constructor for Firebase class binding

    // parameterized constructor
    public Room(String key, String name, String location, String capacity, String admin) {
        this.key = key;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.admin = admin;
    }

    // getters
    public String getKey() { return key; }
    public String getName() { return name; }
    public String getLocation() { return location; }
    public String getCapacity() { return capacity; }
    public String getAdmin() { return admin; }

    // setters
    public Room setKey(String key) { this.key = key; return this; }
    public void setName(String name) { this.name = name; }
    public void setLocation(String location) { this.location = location; }
    public void setCapacity(String capacity) { this.capacity = capacity; }
    public void setAdmin(String admin) { this.admin = admin; }

    // toString method
    public String toString() { return name + ", " + location; }
}
