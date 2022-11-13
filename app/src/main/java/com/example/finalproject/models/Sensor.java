package com.example.finalproject.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Sensor {
    private String key;
    private String roomKey;
    private boolean status;

    // constructors
    public Sensor() {}
    public Sensor(String key, String roomKey, boolean status) {
        this.key = key;
        this.roomKey = roomKey;
        this.status = status;
    }

    // setters
    public Sensor setKey(String key) { this.key = key; return this; }
    public void setRoomKey(String roomKey) { this.roomKey = roomKey; }
    public void setStatus(boolean status) { this.status = status; }

    // getters
    public String getKey() { return key; }
    public String getRoomKey() { return roomKey; }
    public boolean getStatus() { return status; }

    public String toString() { return key; }
}
