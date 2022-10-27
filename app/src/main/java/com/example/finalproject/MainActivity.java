package com.example.finalproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.database.StudyRoomDB;
import com.example.finalproject.database.entity.Sensor;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected RecyclerView RoomRecyclerView;
    protected RoomRecyclerViewAdapter RoomRecyclerViewAdapter;
    protected StudyRoomDB studyRoomDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studyRoomDB = StudyRoomDB.getInstance(this);

        String roomName = "Room " + (int) (Math.random() * 100);
        String roomLocation = "John Molson School of Business";
        String roomStatus = "Available";
        Sensor sensor = new Sensor(0, roomName, roomLocation, roomStatus);
        studyRoomDB.sensorDao().insertAll(sensor);


        setupRecyclerView();
    }

    protected void setupRecyclerView() {
        List<Sensor> RoomList = studyRoomDB.sensorDao().getAll();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RoomRecyclerViewAdapter = new RoomRecyclerViewAdapter(RoomList);

        RoomRecyclerView = findViewById(R.id.Sensor_RecyclerViewID);
        RoomRecyclerView.setLayoutManager(linearLayoutManager);
        RoomRecyclerView.setAdapter(RoomRecyclerViewAdapter);
    }
}