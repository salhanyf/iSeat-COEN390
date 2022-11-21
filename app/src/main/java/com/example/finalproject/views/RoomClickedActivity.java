package com.example.finalproject.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.finalproject.R;

public class RoomClickedActivity extends AppCompatActivity {
    private TextView roomCapacity;
    private TextView roomLocation;
    private TextView roomID;
    private TextView roomDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_clicked);
    }
}