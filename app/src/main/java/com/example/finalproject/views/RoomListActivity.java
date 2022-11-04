package com.example.finalproject.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.finalproject.R;
import com.example.finalproject.controllers.FirebaseDatabaseHelper;
import com.example.finalproject.models.Room;

import java.util.List;

public class RoomListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        progressBar = findViewById(R.id.progressBarRecyclerView);
        mRecyclerView = findViewById(R.id.Room_RecyclerViewID);
        new FirebaseDatabaseHelper().readRooms(new UpdateRoomsRecyclerView());
    }

    private class UpdateRoomsRecyclerView implements FirebaseDatabaseHelper.DataStatusRoom {
        @Override
        public void DataIsLoaded(List<Room> rooms, List<String> keys) {
            if (progressBar.getVisibility() != View.GONE) progressBar.setVisibility(View.GONE);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(RoomListActivity.this));
            mRecyclerView.setAdapter(new RoomListRecyclerViewAdaptor(RoomListActivity.this, rooms, keys));
        }
    }
}