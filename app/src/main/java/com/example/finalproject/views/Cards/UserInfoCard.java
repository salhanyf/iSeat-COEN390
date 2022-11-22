package com.example.finalproject.views.Cards;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.finalproject.R;

public class UserInfoCard extends AppCompatActivity {

    private Button avatarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_card);

        avatarButton = (Button) findViewById(R.id.avatarButton);

    }
}