package com.example.finalproject.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.controllers.FirebaseDatabaseHelper;
import com.example.finalproject.models.Room;
import com.example.finalproject.models.Sensor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RoomListActivity extends AppCompatActivity {

    private FirebaseDatabaseHelper db;
    private RecyclerView mRecyclerView;
    private List<Sensor> sensorList = null;
    private Query querySensorRoomIDs, querySensorStates;
    private ValueEventListener listenerSensorRoomIDs, listenerSensorStates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        mRecyclerView = (RecyclerView) findViewById(R.id.Room_RecyclerViewID);
        db = new FirebaseDatabaseHelper();
        db.readRooms(new UpdateRoomsRecyclerView());

        DatabaseReference ref =  FirebaseDatabase.getInstance().getReference();
        Query query = ref.child("sensors").orderByChild("roomID").startAfter(0);

        ValueEventListener listener = query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sensorList = new ArrayList<>();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    sensorList.add(snap.getValue(Sensor.class));
                }
                db.readRooms(new UpdateRoomsRecyclerView());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RoomListActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onStop() {
        querySensorRoomIDs.removeEventListener(listenerSensorRoomIDs);
        super.onStop();
    }

    private class UpdateRoomsRecyclerView implements FirebaseDatabaseHelper.DataStatus {
        @Override
        public void DataIsLoaded(List<Room> rooms, List<String> keys) {
            findViewById(R.id.progressBarRecyclerView).setVisibility(View.GONE);

            if (sensorList != null) {
                int[] open = new int[rooms.size() + 1];
                int[] total = new int[rooms.size() + 1];

                for (Sensor s : sensorList) {
                    if (s.getStatus() == true)
                        open[s.getRoomID()]++;
                    total[s.getRoomID()]++;
                }

                for (int i = 0; i < rooms.size(); i++) {
                    rooms.get(i).setCapacity(String.format("%d/%d", open[i + 1], total[i + 1]));
                }
            }

            new RecyclerView_Config_item().setConfig(mRecyclerView, RoomListActivity.this, rooms, keys);
        }

        @Override public void DataIsInserted() {}
        @Override public void DataIsUpdated() {}
        @Override public void DataIsDeleted() {}
    }
}