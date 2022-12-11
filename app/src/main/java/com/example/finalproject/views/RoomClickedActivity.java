/*
    File:           RoomClickedActivity.java
    Authors:        Adnan Saab          #40075504
                    Samson Kaller       #40136815
                    Farah Salhany       #40074803
                    Shahin Khalkhali    #40057384
                    Shayan Khalkhali    #40059491
                    Marwan Al-Ghaziri   #40126554
    Description:    This class controls items on the Room Clicked activity. This is a page that pops
                    up when a user clicks a room in the Room List. For now, details are hardcoded for
                    popular Concordia buildings.
*/
package com.example.finalproject.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.finalproject.R;
import com.example.finalproject.controllers.FirebaseDatabaseHelper;
import com.example.finalproject.models.Sensor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class RoomClickedActivity extends AppCompatActivity {
    TextView roomCapacity;
    String roomKey;
    List<Sensor> sensors = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_clicked);

        Toolbar toolbar = findViewById(R.id.toolbar_room_clicked);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        ImageView roomImage = findViewById(R.id.room_info_picture);

        TextView roomLocation = findViewById(R.id.room_info_title);
        TextView roomID = findViewById(R.id.room_info_ID);
        roomCapacity = findViewById(R.id.room_Capacity);
        TextView roomDescription = findViewById(R.id.room_info_description);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            roomKey = bundle.getString("roomKey");
            roomLocation.setText("Location: " + bundle.getString("roomLocation"));
            roomID.setText("Room ID: " + bundle.getString("roomName"));
            roomCapacity.setText("Available Seats: " + bundle.getString("roomCapacity"));
        }
        //get room capacity on change using addValueEventListener
        FirebaseDatabaseHelper firebaseDatabaseHelper = new FirebaseDatabaseHelper();
        UpdateCapacityTextView updateCapacityTextView = new UpdateCapacityTextView(roomCapacity);
        updateCapacityTextView.dataUpdated(sensors);
        firebaseDatabaseHelper.getReferenceSensors().addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sensors.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Sensor sensor = dataSnapshot.getValue(Sensor.class).setKey(dataSnapshot.getKey());
                    if (sensor != null) {
                        if (sensor.getRoomKey().equals(roomKey)) {
                            sensors.add(sensor);
                        }
                    }
                }
                if (sensors.size() == 0) {
                    roomCapacity.setText("Available Seats: 0/0");
                }
                updateCapacityTextView.dataUpdated(sensors);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        TextView toolBarTitle = findViewById(R.id.toolbar_title_room_clicked);

        toolBarTitle.setText(bundle.getString("roomName") + " Room Info");

        if (roomLocation.getText().toString().startsWith("Location: Hall")) {
            roomImage.setImageResource(R.drawable.rooms_hallbuilding);

            roomDescription.setText("The Hall Building is the main building on campus." +
                    " It is home to the majority of the University's academic departments," +
                    " including the School of Engineering and the School of Computing. It " +
                    "is also home to the University's main library, the Sir Duncan Rice " +
                    "Library, and the University's main student union, the Students' Union.");
        } else if (roomLocation.getText().toString().startsWith("Location: Library")) {
            roomImage.setImageResource(R.drawable.rooms_library);

            roomDescription.setText("Concordia University Library is the library of Concordia" +
                    " University in Montreal, Quebec, Canada. It is located on the Sir George" +
                    " Williams Campus in the Hall Building. The library is open to the public" +
                    " and is a member of the Quebec Library Network.");
        } else if (roomLocation.getText().toString().startsWith("Location: Faubourg")) {
            roomImage.setImageResource(R.drawable.rooms_faubourg);

            roomDescription.setText("Faubourg Building is a building on the Sir George Williams" +
                    " Campus of Concordia University in Montreal, Quebec, Canada. It is located" +
                    " on the corner of de Maisonneuve Boulevard and Concordia Avenue.");
        } else if (roomLocation.getText().toString().startsWith("Location: GM")) {
            roomImage.setImageResource(R.drawable.rooms_gm);

            roomDescription.setText("Guy-Maisonnette Building is a building on the Sir George" +
                    " Williams Campus of Concordia University in Montreal, Quebec, Canada. It is" +
                    " located on the corner of de Maisonneuve Boulevard and Concordia Avenue.");
        } else if (roomLocation.getText().toString().startsWith("Location: JMSB") || roomLocation.getText().toString().startsWith("Location: John")) {
            roomImage.setImageResource(R.drawable.rooms_jmsb);

            roomDescription.setText("John Molson School of Business is a business school located " +
                    "in Montreal, Quebec, Canada. It is part of Concordia University, and is " +
                    "named after John Molson, a Montreal businessman and philanthropist.");
        } else {
            roomImage.setImageResource(R.drawable.error);
            roomDescription.setText("No description available.");
        }

    }

    private class UpdateCapacityTextView implements FirebaseDatabaseHelper.SensorDataChange {
        private final TextView textView;

        public UpdateCapacityTextView(TextView textView) {
            this.textView = textView;
        }

        @Override
        public void dataUpdated(List<Sensor> sensors) {
            int open = 0, total = 0;
            for (Sensor sensor : sensors) {
                if (sensor.getStatus()) {
                    open++;
                }
                total++;
                Log.w("RoomClickedActivity", "dataUpdated: " + sensor.getStatus() + "Capacity: " + open + "/" + total);
                textView.setText("Available Seats: " + open + "/" + total);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}

