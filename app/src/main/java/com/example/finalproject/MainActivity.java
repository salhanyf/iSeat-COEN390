package com.example.finalproject;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

//        StudyRoomDB db = StudyRoomDB.getInstance(getApplicationContext());


        /*
        // Just testing can delete.
        for(int roomNumber = 1; roomNumber <= 10; roomNumber++) {
            for(int floorNumber = 1; floorNumber <= 10; floorNumber++) {
                String roomID = "ID: " + roomNumber + floorNumber;
                String roomLocation = "Room " + roomNumber + ", Floor " + 1;
                String roomStatus = "Available";
                Sensor sensor = new Sensor(roomNumber, roomID, roomLocation, roomStatus);
                db.sensorDao().insertAll(sensor);
            }
            }*/
        }
    }

}
