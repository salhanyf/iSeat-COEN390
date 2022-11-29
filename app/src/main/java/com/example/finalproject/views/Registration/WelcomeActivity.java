package com.example.finalproject.views.Registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.finalproject.R;
import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity {

    private Button gettingStartedButton, loginHereButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            finish();

        gettingStartedButton = findViewById(R.id.gettingStartedButton);
        loginHereButton = findViewById(R.id.loginRedirect);

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
    }
}