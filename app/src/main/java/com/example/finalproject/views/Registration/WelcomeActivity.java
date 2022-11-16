package com.example.finalproject.views.Registration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.views.AdminRoomsActivity;
import com.example.finalproject.views.RoomListActivity;
import com.google.firebase.auth.FirebaseAuth;

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

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            signOutTestButton();
    }

    private void signOutTestButton() {
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        params.topToTop = R.id.layoutWelcome;
        params.endToEnd = R.id.layoutWelcome;
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
                button.setVisibility(View.GONE);
            }
        });
        ((ConstraintLayout) findViewById(R.id.layoutWelcome)).addView(button);
    }
}