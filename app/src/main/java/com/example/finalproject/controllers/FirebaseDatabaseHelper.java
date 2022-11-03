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
    private final FirebaseDatabase mDatabase; // Firebase database object
    private final DatabaseReference mReferenceProfiles; // Object for Adnan and Farah
    private final DatabaseReference mReferenceRooms;  // Object for Shahin, Shayan, and Samson
    private final DatabaseReference mReferenceSensors; // Object for Shahin, Shayan, and Samson
    private final List<Pair<Query, ValueEventListener>> queries = new ArrayList<>();
    private final List<Room> rooms = new ArrayList<>();

    public interface DataStatus {
        void DataIsLoaded(List<Room> rooms);
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
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    Room room = keyNode.getValue(Room.class);
                    rooms.add(room);
                    listenToRoomSensors(keyNode.getKey(), room, dataStatus);
                    Log.i("Room", (room == null ? "Error: room == null" : room.getName()));
                }
                dataStatus.DataIsLoaded(rooms);
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
                    if ((sensor != null) && sensor.getStatus())
                        open++;
                    total++;
                }
                room.setCapacity(open + "/" + total);
                dataStatus.DataIsLoaded(rooms);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        queries.add(new Pair<>(q, l));
    }
}
