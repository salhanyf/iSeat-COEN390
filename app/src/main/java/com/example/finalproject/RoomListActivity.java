package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.finalproject.controllers.FirebaseDatabaseHelper;
import com.example.finalproject.models.Room;

import java.util.List;

public class RoomListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seats_user);

        mRecyclerView = (RecyclerView) findViewById(R.id.Room_RecyclerViewID);

        new FirebaseDatabaseHelper().readRooms(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Room> rooms, List<String> keys) {
                findViewById(R.id.progressBarRecyclerView).setVisibility(View.GONE);
                new RecyclerView_Config_item().setConfig(mRecyclerView, RoomListActivity.this, rooms, keys);
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
    }
}