package com.example.finalproject.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.finalproject.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (FirebaseAuth.getInstance().getCurrentUser() == null)
            startActivity(new Intent(this, LoginActivity.class));

        // TEST BUTTONS FOR DEMO:
        signOutTestButton();            // allows signed-in user to sign out
        seatsActivityTestButton();      // goes to seat activity
        addRemoveSensorsTestButtons();  // adds or removes sensor from room 1

    }

    private void signOutTestButton() {
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        params.topToTop = R.id.constraintLayoutMainActivity;
        params.endToEnd = R.id.constraintLayoutMainActivity;
        params.topMargin = 25;
        params.rightMargin = 25;
        Button button = new Button(this);
        button.setLayoutParams(params);
        button.setBackgroundColor(getColor(R.color.white));
        button.setTextColor(0xFF0000FF);
        button.setText("  Sign out  ");
        button.setOnClickListener(view -> {
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                String username = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "User: " + username + " logged off.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
            }
        });
        ((ConstraintLayout) findViewById(R.id.constraintLayoutMainActivity)).addView(button);
    }

    private void seatsActivityTestButton() {
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        params.startToStart = R.id.constraintLayoutMainActivity;
        params.endToEnd = R.id.constraintLayoutMainActivity;
        params.topToTop = R.id.constraintLayoutMainActivity;
        params.topMargin = 250;
        Button button = new Button(this);
        button.setLayoutParams(params);
        button.setBackgroundColor(getColor(R.color.purple_700));
        button.setTextColor(getColor(R.color.white));
        button.setText("  Goto Seats Activity  ");
        button.setOnClickListener(view -> {
            Intent intent = new Intent(this, RoomListActivity.class);
            startActivity(intent);
        });
        ((ConstraintLayout) findViewById(R.id.constraintLayoutMainActivity)).addView(button);
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

        ConstraintLayout.LayoutParams paramsButtonAdd = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        paramsButtonAdd.startToStart = R.id.constraintLayoutMainActivity;
        paramsButtonAdd.endToEnd = R.id.constraintLayoutMainActivity;
        paramsButtonAdd.bottomToBottom = R.id.constraintLayoutMainActivity;
        paramsButtonAdd.bottomMargin = 3 * MARGIN;
        buttonAdd.setLayoutParams(paramsButtonAdd);

        ConstraintLayout.LayoutParams paramsButtonRemove = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
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
