package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.finalproject.models.Room;

import java.util.List;

public class RoomInfoActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView; // NEW

    // TextView ROOM_NAME; // OLD

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seats_user);

        // NEW
        mRecyclerView = (RecyclerView) findViewById(R.id.Room_RecyclerViewID);

        new FirebaseDatabaseHelper().readRooms(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Room> rooms, List<String> keys) {
                new RecyclerView_Config_item().setConfig(mRecyclerView, RoomInfoActivity.this, rooms, keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });

        // OLD
        /*
        ROOM_NAME = findViewById(R.id.Room_info_id);
        ROOM_NAME.setText("-THIS IS ROOM INFO-");

        Intent intent = getIntent();

        int studyRoomID = intent.getIntExtra("room_uid", 0);
        if(studyRoomID != 0) {
            AppDatabase db = AppDatabase.getInstance(this);
            Room roomEntity = db.roomDao().findById(studyRoomID);

            ROOM_NAME.setText("Room Number: " +  roomEntity.getName() + "\nRoom Location: " + roomEntity.getLocation() + "\nRoom's Capacity: " + roomEntity.getCapacity());
        }
        intent.getStringExtra("roomLocation");
        intent.getStringExtra("roomStatus");
        */

    }
}