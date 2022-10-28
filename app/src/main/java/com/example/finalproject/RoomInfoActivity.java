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

        int uid = intent.getIntExtra("roomNumber", 0);
        if(uid != 0) {
            StudyRoomDB db = StudyRoomDB.getInstance(this);
            RoomEntity roomEntity = db.sensorDao().findBtId(uid);

            ROOM_NAME.setText(roomEntity.getStudyRoomID() + " " + roomEntity.getStudyRoomName() + " " + roomEntity.getStudyRoomLocation() + " " + roomEntity.getStudyRoomStatus());
        }
        intent.getStringExtra("roomLocation");
        intent.getStringExtra("roomStatus");
    }
}