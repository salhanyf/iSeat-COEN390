/*
    File:           Sensor.java
    Authors:        Adnan Saab          #40075504
                    Samson Kaller       #40136815
                    Farah Salhany       #40074803
                    Shahin Khalkhali    #40057384
                    Shayan Khalkhali    #40059491
                    Marwan Al-Ghaziri   #40126554
    Description:    This class provides the Sensor model class that is used to hold data on a sensor
                    throughout the app. Data attributes match the Sensor nodes on Firebase.
*/
package com.example.finalproject.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Sensor {
    private String key;
    private String roomKey;
    private boolean status;

    public Sensor() {} // empty constructor for Firebase class binding

    // parameterized constructor
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

    // convert to string
    public String toString() { return key; }
}
