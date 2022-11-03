package com.example.finalproject.models;

import androidx.annotation.NonNull;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.annotations.NotNull;

@IgnoreExtraProperties
public class Room {
    private String name;
    private String location;
    private String capacity;
    private String admin;

    public Room() {}

    public Room(String name, String location, String capacity, String admin) {
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.admin = admin;
    }

    // getters
    public String getName() { return name; }
    public String getLocation() { return location; }
    public String getCapacity() { return capacity; }
    public String getAdmin() { return admin; }

    // setters
    public void setName(String name) { this.name = name; }
    public void setLocation(String location) { this.location = location; }
    public void setCapacity(String capacity) { this.capacity = capacity; }
    public void setAdmin(String admin) { this.admin = admin; }

    // toString method
    public String toString() { return name + ", " + location; }
}
