package com.example.finalproject.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Sensor {
    private String roomId;
    private boolean state;

    public Sensor(){}

    public Sensor(String roomId, boolean state) {
        this.roomId = roomId;
        this.state = state;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean getState() {
        return state;
    }
}
