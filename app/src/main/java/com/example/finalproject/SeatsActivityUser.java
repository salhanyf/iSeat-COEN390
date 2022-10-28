package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.finalproject.database.StudyRoomDB;
import com.example.finalproject.database.entity.RoomEntity;

import java.util.List;

public class SeatsActivityUser extends AppCompatActivity {
    protected RecyclerView RoomRecyclerView;
    protected RoomRecyclerViewAdapter RoomRecyclerViewAdapter;
    protected StudyRoomDB studyRoomDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seats_user);
        studyRoomDB = StudyRoomDB.getInstance(this);

        String roomName = "Room " + (int) (Math.random() * 100);
        String roomLocation = "John Molson School of Business";
        String roomStatus = "Available";
        int RandomSeats = (int) (Math.random() * 6);
        String roomSeats = Integer.toString(RandomSeats) + "/6";
        RoomEntity roomEntity = new RoomEntity(0, roomName, roomLocation, roomStatus, roomSeats);
        studyRoomDB.sensorDao().insertAll(roomEntity);

        setupRecyclerView();
    }

    protected void setupRecyclerView() {
        List<RoomEntity> roomEntityList = studyRoomDB.sensorDao().getAll();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RoomRecyclerViewAdapter = new RoomRecyclerViewAdapter(roomEntityList);

        RoomRecyclerView = findViewById(R.id.Sensor_RecyclerViewID);
        RoomRecyclerView.setLayoutManager(linearLayoutManager);
        RoomRecyclerView.setAdapter(RoomRecyclerViewAdapter);

    }
}