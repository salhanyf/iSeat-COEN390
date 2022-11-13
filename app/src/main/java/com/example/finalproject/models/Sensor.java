package com.example.finalproject.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Sensor {
    private String key;
    private String roomID;
    private boolean status;

    // constructors
    public Sensor() {}
    public Sensor(String key, String roomID, boolean status) {
        this.key = key;
        this.roomID = roomID;
        this.status = status;
    }

    // setters
    public Sensor setKey(String key) { this.key = key; return this; }
    public void setRoomID(String roomID) { this.roomID = roomID; }
    public void setStatus(boolean status) { this.status = status; }

    // getters
    public String getKey() { return key; }
    public String getRoomID() { return roomID; }
    public boolean getStatus() { return status; }

    public String toString() { return key; }
}
