package com.example.finalproject.controllers;

import android.util.Log;

import androidx.annotation.NonNull;
import com.example.finalproject.models.Room;
import com.example.finalproject.models.Sensor;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private final FirebaseDatabase mDatabase; // Firebase database object
    private final DatabaseReference mReferenceProfiles; // Object for Adnan and Farah
    private final DatabaseReference mReferenceRooms;  // Object for Shahin, Shayan, and Samson
    private final DatabaseReference mReferenceSensors; // Object for Shahin, Shayan, and Samson
    private final List<Room> rooms = new ArrayList<>();
    private final List<String> keys = new ArrayList<>();

    public interface DataStatusRoom {
        void DataIsLoaded(List<Room> rooms, List<String> keys);
//        void DataIsInserted(); void DataIsUpdated(); void DataIsDeleted();
    }

    public interface DataStatusSensor {
        void DataIsLoaded(List<Sensor> sensors);
//        void DataIsInserted(); void DataIsUpdated(); void DataIsDeleted();
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
                rooms.clear();
                keys.clear();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    Room room = keyNode.getValue(Room.class);
                    rooms.add(room);
                    Log.i("Room", (room == null ? "Error: room == null" : room.getName()));
                }
                dataStatus.DataIsLoaded(rooms, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getRoomSensors(String roomKey, DataStatusSensor dataStatus) {
        mReferenceSensors.orderByChild("roomID").equalTo(Integer.parseInt(roomKey)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Sensor> sensors = new ArrayList<>();
                for (DataSnapshot snap : snapshot.getChildren()) {
                     sensors.add(snap.getValue(Sensor.class));
                }
                dataStatus.DataIsLoaded(sensors);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
