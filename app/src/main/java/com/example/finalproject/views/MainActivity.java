package com.example.finalproject.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.finalproject.R;
import com.example.finalproject.views.Registration.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
//        if (FirebaseAuth.getInstance().getCurrentUser() == null)
//            startActivity(new Intent(this, LoginActivity.class));
//        else
//            adminRoomsActivityTestButton();      // goes to room activity
//
//        // TEST BUTTONS FOR DEMO:
//        signOutTestButton();            // allows signed-in user to sign out
//        seatsActivityTestButton();
    }

//    private void signOutTestButton() {
//        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
//        params.topToTop = R.id.constraintLayoutMainActivity;
//        params.endToEnd = R.id.constraintLayoutMainActivity;
//        params.topMargin = 25;
//        params.rightMargin = 25;
//        Button button = new Button(this);
//        button.setLayoutParams(params);
//        button.setBackgroundColor(getColor(R.color.white));
//        button.setTextColor(0xFF0000FF);
//        button.setText("  Sign out  ");
//        button.setOnClickListener(view -> {
//            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//                String username = FirebaseAuth.getInstance().getCurrentUser().getEmail();
//                FirebaseAuth.getInstance().signOut();
//                Toast.makeText(this, "User: " + username + " logged off.", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(this, LoginActivity.class));
//            }
//        });
//        ((ConstraintLayout) findViewById(R.id.constraintLayoutMainActivity)).addView(button);
//    }
//
//    private void seatsActivityTestButton() {
//        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
//        params.startToStart = R.id.constraintLayoutMainActivity;
//        params.endToEnd = R.id.constraintLayoutMainActivity;
//        params.topToTop = R.id.constraintLayoutMainActivity;
//        params.topMargin = 400;
//        Button button = new Button(this);
//        button.setLayoutParams(params);
//        button.setBackgroundColor(getColor(R.color.purple_700));
//        button.setTextColor(getColor(R.color.white));
//        button.setText("  Goto Room List Activity  ");
//        button.setOnClickListener(view -> startActivity(new Intent(this, RoomListActivity.class)));
//        ((ConstraintLayout) findViewById(R.id.constraintLayoutMainActivity)).addView(button);
//    }
//
//    private void adminRoomsActivityTestButton() {
//        String adminEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
//        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
//        params.startToStart = R.id.constraintLayoutMainActivity;
//        params.endToEnd = R.id.constraintLayoutMainActivity;
//        params.bottomToBottom = R.id.constraintLayoutMainActivity;
//        params.bottomMargin = 400;
//        Button button = new Button(this);
//        button.setLayoutParams(params);
//        button.setBackgroundColor(getColor(R.color.purple_700));
//        button.setTextColor(getColor(R.color.white));
//        button.setAllCaps(false);
//        button.setText(String.format("  GOTO ADMIN ROOMS ACTIVITY FOR  \n  %s  ", adminEmail));
//        button.setOnClickListener(view -> {
//            startActivity(new Intent(this, AdminRoomsActivity.class).putExtra(getString(R.string.Extra_adminEmail), FirebaseAuth.getInstance().getCurrentUser().getEmail()));
//        });
//        ((ConstraintLayout) findViewById(R.id.constraintLayoutMainActivity)).addView(button);
//    }
}
