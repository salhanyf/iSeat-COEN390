package com.example.finalproject.views.Registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.finalproject.R;
import com.example.finalproject.views.AdminRoomsActivity;
import com.example.finalproject.views.RoomListActivity;

public class WelcomeActivity extends AppCompatActivity {

    private Button gettingStartedButton, loginHereButton;
    private Button testAdmin, testUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //TODO: Check if user already signed in - if yes check if user is admin or not and go straight to the room activity

        gettingStartedButton = findViewById(R.id.gettingStartedButton);
        loginHereButton = findViewById(R.id.loginRedirect);

        testAdmin = findViewById(R.id.adminRedirect);
        testUser = findViewById(R.id.userRedirect);

        //redirect to sign up
        gettingStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(WelcomeActivity.this, RegisterActivity.class));
            }
        });

        //redirect to sign in
        loginHereButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(WelcomeActivity.this, LoginActivity.class));
            }
        });

        testAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(WelcomeActivity.this, AdminRoomsActivity.class));
            }
        });

        testUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(WelcomeActivity.this, RoomListActivity.class));
            }
        });
    }
}