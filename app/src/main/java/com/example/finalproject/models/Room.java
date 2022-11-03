package com.example.finalproject.models;

import androidx.annotation.NonNull;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.annotations.NotNull;

@IgnoreExtraProperties
public class Room {
    private String name;
    private String location;
    private String capacity;

    public Room() {}

    public Room(String name, String location) {
        this.name = name;
        this.location = location;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getCapacity() {
        return capacity;
    }

    // DELETE SOON
    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String toString() {
        return name + ", " + location;
    }
}
