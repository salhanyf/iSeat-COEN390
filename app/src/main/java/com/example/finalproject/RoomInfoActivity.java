package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.finalproject.database.StudyRoomDB;
import com.example.finalproject.database.entity.RoomEntity;

public class RoomInfoActivity extends AppCompatActivity {

    TextView ROOM_NAME;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_info);

        ROOM_NAME = findViewById(R.id.Room_info_id);
        ROOM_NAME.setText("-THIS IS ROOM INFO-");

        Intent intent = getIntent();

        int studyRoomID = intent.getIntExtra("room_uid", 0);
        if(studyRoomID != 0) {
            StudyRoomDB db = StudyRoomDB.getInstance(this);
            RoomEntity roomEntity = db.sensorDao().findById(studyRoomID);

            ROOM_NAME.setText("Room Number: " +  roomEntity.getStudyRoomID() + "\nRoom ID: " + roomEntity.getStudyRoomName() + "\nRoom Location: " + roomEntity.getStudyRoomLocation() + "\nRoom's Status: " + roomEntity.getStudyRoomStatus() + "\nRoom's Capacity: " + roomEntity.getStudyRoomSeats());
        }
        intent.getStringExtra("roomLocation");
        intent.getStringExtra("roomStatus");
    }
}