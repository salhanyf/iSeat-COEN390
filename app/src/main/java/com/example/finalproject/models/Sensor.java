package com.example.finalproject.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Sensor {
    private int roomID;
    private boolean status;

    public Sensor(){}

    public Sensor(int roomID, boolean status) {
        this.roomID = roomID;
        this.status = status;
    }

    public int getRoomID() {
        return roomID;
    }

    public boolean getStatus() {
        return status;
    }
}
