package com.example.finalproject.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Sensor {
    private String key;
    private int roomID;
    private boolean status;

    // constructors
    public Sensor() {}
    public Sensor(String key, int roomID, boolean status) {
        this.key = key;
        this.roomID = roomID;
        this.status = status;
    }

    // setters
    public Sensor setKey(String key) { this.key = key; return this; }
    public void setRoomID(int roomID) { this.roomID = roomID; }
    public void setStatus(boolean status) { this.status = status; }

    // getters
    public String getKey() { return key; }
    public int getRoomID() { return roomID; }
    public boolean getStatus() { return status; }
}
