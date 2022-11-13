package com.example.finalproject.controllers;

import android.util.Log;

import androidx.annotation.NonNull;
import com.example.finalproject.models.Room;
import com.example.finalproject.models.Sensor;
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
    private final DatabaseReference mReferenceRooms;  // Object for Shahin, Shayan, and Samson
    private final DatabaseReference mReferenceSensors; // Object for Shahin, Shayan, and Samson
    Query query;
    ValueEventListener listener;

    public interface DataStatusRoom {
        void DataIsLoaded(List<Room> rooms);
//        void DataIsInserted(); void DataIsUpdated(); void DataIsDeleted();
    }

    public interface SensorDataChange {
        void dataUpdated(List<Sensor> sensors);
    }

    public FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceProfiles = mDatabase.getReference("profiles"); // Make sure this is correct for Adnan and Farah
        mReferenceRooms = mDatabase.getReference("rooms");
        mReferenceSensors = mDatabase.getReference("sensors");
    }

    public void readRooms(DataStatusRoom dataStatus) {
        mReferenceRooms.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {  // This is the method that is called when the data is changed (asynchronous)
                List<Room> rooms = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    Room room = keyNode.getValue(Room.class);
                    rooms.add(room.setKey(keyNode.getKey()));
                    Log.i("Room", (room == null ? "Error: room == null" : room.getName()));
                }
                dataStatus.DataIsLoaded(rooms);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void listenToSensorsRoom(String roomKey, SensorDataChange dataStatus) {
        query = mReferenceSensors.orderByChild("roomKey").equalTo(roomKey);
        listener = query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Sensor> sensors = new ArrayList<>();
                for (DataSnapshot snap : snapshot.getChildren()) {
                     sensors.add(snap.getValue(Sensor.class).setKey(snap.getKey()));
                }
                dataStatus.dataUpdated(sensors);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void removeSensorsListeners() {
        query.removeEventListener(listener);
    }

    public void getAdminRooms(String adminEmail, DataStatusRoom dataStatus) {
        mReferenceRooms.orderByChild("admin").equalTo(adminEmail).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Room> rooms = new ArrayList<>();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    rooms.add(snap.getValue(Room.class).setKey(snap.getKey()));
                }
                dataStatus.DataIsLoaded(rooms);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
