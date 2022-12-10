/*
    File:           FirebaseDatabaseHelper.java
    Authors:        Adnan Saab          #40075504
                    Samson Kaller       #40136815
                    Farah Salhany       #40074803
                    Shahin Khalkhali    #40057384
                    Shayan Khalkhali    #40059491
                    Marwan Al-Ghaziri   #40126554
    Description:    This class provides helper functions for reading/writing data to Firebase.
*/
package com.example.finalproject.controllers;

import android.util.Log;

import androidx.annotation.NonNull;
import com.example.finalproject.models.Room;
import com.example.finalproject.models.Sensor;
import com.example.finalproject.models.User;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private final FirebaseDatabase mDatabase; // Firebase database object
    private final DatabaseReference mReferenceProfiles; // Object for Adnan and Farah
    private final DatabaseReference mReferenceUsers;
    private final DatabaseReference mReferenceRooms;  // Object for Shahin, Shayan, and Samson
    private final DatabaseReference mReferenceSensors; // Object for Shahin, Shayan, and Samson

    // query and listener for a list of sensor in room
    Query query;
    ValueEventListener listener;

    // method used to delete a user from the database
    public static void deleteUser(String uEmail) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        // search for the user with the email
        myRef.orderByChild("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // for all returned data snapshots
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.d("TAG", "onDataChange: " + ds.child("email").getValue() + " VS " + uEmail);
                    // compare email and remove if its the desired user to delete
                    if (ds.child("email").getValue().equals(uEmail)) {
                        ds.getRef().removeValue();
                    }
                }
            }

            // when firebase connected fails
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("TAG", "onCancelled: " + databaseError.getMessage());
            }
        });
    }

    public String[] getAdminInfo() {
        final String[] strings = new String[3];
        query = mReferenceProfiles.orderByChild("username").equalTo("admin");
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    strings[0] = ds.child("username").getValue(String.class);
                    strings[1] = ds.child("email").getValue(String.class);
                    strings[2] = ds.child("password").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Error", error.getMessage());
            }
        };
        query.addListenerForSingleValueEvent(listener);
        return strings;
    }

    // interface to design custom DataIsLoaded implementation in calling file
    public interface DataStatusRoom {
        void DataIsLoaded(List<Room> rooms);
    }

    // interface to design custom dataUpdated implementation in calling file
    public interface SensorDataChange {
        void dataUpdated(List<Sensor> sensors);
    }

    // constructor
    public FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceProfiles = mDatabase.getReference("Profiles"); // Make sure this is correct for Adnan and Farah
        mReferenceUsers = mDatabase.getReference("users");
        mReferenceRooms = mDatabase.getReference("rooms");
        mReferenceSensors = mDatabase.getReference("sensors");
    }

    // Read all rooms from the database
    public void readRooms(DataStatusRoom dataStatus) {
        mReferenceRooms.addValueEventListener(new ValueEventListener() {
            @Override
            // This is the method that is called when the data is changed (asynchronous)
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // create empty list
                List<Room> rooms = new ArrayList<>();
                // for all the return data snapshots
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    Room room = keyNode.getValue(Room.class);   // get the room Key
                    rooms.add(room.setKey(keyNode.getKey()));   // add it to list while setting the key
                    Log.i("Room", (room == null ? "Error: room == null" : room.getName()));
                }
                // call the interface for data loaded
                dataStatus.DataIsLoaded(rooms);
            }

            // problem loading data
            @Override public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    // return the list of sensor nodes associated with the given roomKey
    public void listenToSensorsRoom(String roomKey, SensorDataChange dataStatus) {
        query = mReferenceSensors.orderByChild("roomKey").equalTo(roomKey); // create the query
        listener = query.addValueEventListener(new ValueEventListener() {   // add listener to query
            @Override
            // This is the method that is called when the data is changed (asynchronous)
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Sensor> sensors = new ArrayList<>();   // create new list
                for (DataSnapshot snap : snapshot.getChildren()) // add all returned sensors to list
                    sensors.add(snap.getValue(Sensor.class).setKey(snap.getKey()));
                dataStatus.dataUpdated(sensors);    // call the interface for data Updated
            }

            // problem loading data
            @Override public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    // call to remove sensor queries and listeners when closing calling activity/dialog fragment
    public void removeSensorsListeners() {
        query.removeEventListener(listener);
    }

    // This method is used to obtain a list of the rooms being managed by this Admin User
    public void getAdminRooms(String adminEmail, DataStatusRoom dataStatus) {
        // make query and add listener
        mReferenceRooms.orderByChild("admin").equalTo(adminEmail).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Room> rooms = new ArrayList<>();               // new list
                for (DataSnapshot snap : snapshot.getChildren())    // add all returned data to list
                    rooms.add(snap.getValue(Room.class).setKey(snap.getKey())); // add room while setting key
                dataStatus.DataIsLoaded(rooms);
            }

            // problem loading data
            @Override public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    //make a method that reads the users from the database and sets then to the User class
    public void readUsers() {
        mReferenceUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {  // This is the method that is called when the data is changed (asynchronous)
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    User user = keyNode.getValue(User.class);
                    user.setUsername(keyNode.child("username").getValue(String.class));
                    user.setEmail(keyNode.child("email").getValue(String.class));
                    user.setIsAdmin(keyNode.child("isAdmin").getValue(Boolean.class));
                    user.setDateCreated(keyNode.child("dateCreated").getValue(String.class));
                    user.setKey(keyNode.getKey());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    // getters
    public DatabaseReference getReferenceProfiles() { return mReferenceProfiles; }
    public DatabaseReference getReferenceRooms() { return mReferenceRooms; }
    public DatabaseReference getReferenceSensors() { return mReferenceSensors; }

}
