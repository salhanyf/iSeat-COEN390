package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.finalproject.database.AppDatabase;
import com.example.finalproject.database.entity.RoomEntity;

import java.util.List;

public class SeatsActivityUser extends AppCompatActivity {
//    protected RecyclerView RoomRecyclerView;
//    protected RecyclerView_Config_item RoomRecyclerViewAdapter;
//    protected AppDatabase db;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_seats_user);
//        db = AppDatabase.getInstance(this);
//
//        String roomName = "Room " + (int) (Math.random() * 100);
//        String roomLocation = "John Molson School of Business";
//        String roomStatus = "Available";
//        int RandomSeats = (int) (Math.random() * 6);
//        String roomSeats = Integer.toString(RandomSeats) + "/6"; // Input the number of seats available in the room from firebase
//        RoomEntity roomEntity = new RoomEntity(0, roomName, roomLocation, roomStatus, roomSeats);
//        db.roomDao().insertAll(roomEntity);
//
//        setupRecyclerView();
//    }
//
//    protected void setupRecyclerView() {
//        List<RoomEntity> roomEntityList = db.roomDao().getAll();
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        RoomRecyclerViewAdapter = new RecyclerView_Config_item(
//
//        RoomRecyclerView = findViewById(R.id.Room_RecyclerViewID);
//        RoomRecyclerView.setLayoutManager(linearLayoutManager);
//        RoomRecyclerView.setAdapter(RoomRecyclerViewAdapter);
//    }
}