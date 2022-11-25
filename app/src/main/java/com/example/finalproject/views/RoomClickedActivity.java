package com.example.finalproject.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class RoomClickedActivity extends AppCompatActivity {
    TextView roomCapacity;
    String roomKey;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_clicked);


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
            roomCapacity.setText("Seats Available: " + bundle.getString("roomCapacity"));
        }
        //get room capacity on change using addValueEventListener
        FirebaseDatabase.getInstance().getReference("rooms").child(roomKey).child("capacity").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.i("RoomClickedActivity", "!@!@!@!@!onDataChange:!@@!@!@!@!!@ " + dataSnapshot.getValue());
                    roomCapacity.setText("Seats Available: " + dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        TextView toolBarTitle = findViewById(R.id.toolbar_title_room_clicked);

        toolBarTitle.setText(bundle.getString("roomName") + " Room Info");

        if (roomLocation.getText().toString().startsWith("Location: Hall")) {
            roomImage.setImageResource(R.drawable.hallbuilding);

            roomDescription.setText("The Hall Building is the main building on campus." +
                    " It is home to the majority of the University's academic departments," +
                    " including the School of Engineering and the School of Computing. It " +
                    "is also home to the University's main library, the Sir Duncan Rice " +
                    "Library, and the University's main student union, the Students' Union.");
        } else if (roomLocation.getText().toString().startsWith("Location: Library")) {
            roomImage.setImageResource(R.drawable.library);

            roomDescription.setText("Concordia University Library is the library of Concordia" +
                    " University in Montreal, Quebec, Canada. It is located on the Sir George" +
                    " Williams Campus in the Hall Building. The library is open to the public" +
                    " and is a member of the Quebec Library Network.");
        } else if (roomLocation.getText().toString().startsWith("Location: Faubourg")) {
            roomImage.setImageResource(R.drawable.faubourg);

            roomDescription.setText("Faubourg Building is a building on the Sir George Williams" +
                    " Campus of Concordia University in Montreal, Quebec, Canada. It is located" +
                    " on the corner of de Maisonneuve Boulevard and Concordia Avenue.");
        } else if (roomLocation.getText().toString().startsWith("Location: GM")) {
            roomImage.setImageResource(R.drawable.gm);

            roomDescription.setText("Guy-Maisonnette Building is a building on the Sir George" +
                    " Williams Campus of Concordia University in Montreal, Quebec, Canada. It is" +
                    " located on the corner of de Maisonneuve Boulevard and Concordia Avenue.");
        } else if (roomLocation.getText().toString().startsWith("Location: JMSB") || roomLocation.getText().toString().startsWith("Location: John")) {
            roomImage.setImageResource(R.drawable.jmsb);

            roomDescription.setText("John Molson School of Business is a business school located " +
                    "in Montreal, Quebec, Canada. It is part of Concordia University, and is " +
                    "named after John Molson, a Montreal businessman and philanthropist.");
        } else {
            roomImage.setImageResource(R.drawable.error);
            roomDescription.setText("No description available.");
        }

    }

}
