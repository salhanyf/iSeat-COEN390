package com.example.finalproject.models;

import androidx.annotation.NonNull;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.annotations.NotNull;

@IgnoreExtraProperties
public class Room {
    private String name;
    private String location;

    public Room() {}

    public Room(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String toString() {
        return name + ", " + location;
    }
}
