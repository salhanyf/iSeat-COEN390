package com.example.finalproject.controllers;

import android.util.Log;
import android.util.Pair;

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
    private FirebaseDatabase mDatabase; // Firebase database object
    private DatabaseReference mReferenceProfiles; // Object for Adnan and Farah
    private DatabaseReference mReferenceRooms;  // Object for Shahin, Shayan, and Samson
    private DatabaseReference mReferenceSensors; // Object for Shahin, Shayan, and Samson
    private List<Pair<Query, ValueEventListener>> queries = new ArrayList<>();
    private List<String> keys = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();

    public interface DataStatus {
        void DataIsLoaded(List<Room> rooms, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceProfiles = mDatabase.getReference("profiles"); // Make sure this is correct for Adnan and Farah
        mReferenceRooms = mDatabase.getReference("rooms");
        mReferenceSensors = mDatabase.getReference("sensors");
    }

    public void readRooms(DataStatus dataStatus) {
        mReferenceRooms.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {  // This is the method that is called when the data is changed (asynchronous)
                for (Pair<Query, ValueEventListener> p : queries)
                    p.first.removeEventListener(p.second);
                queries.clear();
                rooms.clear();
                keys.clear();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    Room room = keyNode.getValue(Room.class);
                    rooms.add(room);
                    listenToRoomSensors(keyNode.getKey(), room, dataStatus);
                    Log.i("Room", room.getName());
                }
                dataStatus.DataIsLoaded(rooms, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void listenToRoomSensors(String roomKey, Room room, DataStatus dataStatus) {
        Query q = mReferenceSensors.orderByChild("roomID").equalTo(Integer.parseInt(roomKey));
        ValueEventListener l = q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int open = 0, total = 0;
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Sensor sensor = snap.getValue(Sensor.class);
                    if (sensor.getStatus())
                        open++;
                    total++;
                }
                room.setCapacity(open + "/" + total);
                dataStatus.DataIsLoaded(rooms, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        queries.add(new Pair<>(q, l));
    }
}
