package com.example.finalproject;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: check logged-in status and start login activity if not
//        startActivity(new Intent(this, LoginActivity.class));

        addRemoveSensorsTestButtons();

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

    private void addRemoveSensorsTestButtons() {
        final int MARGIN = 100;

        Button buttonAdd = new Button(this);
        buttonAdd.setBackgroundColor(0xFF00A000);
        buttonAdd.setTextColor(getColor(R.color.white));
        buttonAdd.setText("  Add Sensor to room 1  ");
        buttonAdd.setOnClickListener(view -> (new AddSensorDialogFragment("1")).show(getSupportFragmentManager(), "AddSensorDialogFragment"));

        Button buttonRemove = new Button(this);
        buttonRemove.setBackgroundColor(0xFFC00000);
        buttonRemove.setTextColor(getColor(R.color.white));
        buttonRemove.setText("  Remove Sensor from room 1  ");
        buttonRemove.setOnClickListener(view -> (new RemoveSensorDialogFragment("1")).show(getSupportFragmentManager(), "RemoveSensorDialogFragment"));

        ConstraintLayout.LayoutParams paramsButtonAdd = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsButtonAdd.startToStart = R.id.constraintLayoutMainActivity;
        paramsButtonAdd.endToEnd = R.id.constraintLayoutMainActivity;
        paramsButtonAdd.bottomToBottom = R.id.constraintLayoutMainActivity;
        paramsButtonAdd.bottomMargin = 3 * MARGIN;
        buttonAdd.setLayoutParams(paramsButtonAdd);

        ConstraintLayout.LayoutParams paramsButtonRemove = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsButtonRemove.startToStart = R.id.constraintLayoutMainActivity;
        paramsButtonRemove.endToEnd = R.id.constraintLayoutMainActivity;
        paramsButtonRemove.bottomToBottom = R.id.constraintLayoutMainActivity;
        paramsButtonRemove.bottomMargin = MARGIN;
        buttonRemove.setLayoutParams(paramsButtonRemove);

        ConstraintLayout layout = findViewById(R.id.constraintLayoutMainActivity);
        layout.addView(buttonAdd);
        layout.addView(buttonRemove);
    }
}
