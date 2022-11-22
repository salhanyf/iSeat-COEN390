package com.example.finalproject.views.Cards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.finalproject.R;
import com.example.finalproject.views.UserProfileActivity;
import com.example.finalproject.views.dialogfragments.insertAvatarDialogueFragment;

public class UserInfoCard extends AppCompatActivity {

    private Button avatarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_card);


        // TODO: FIX THIS
        avatarButton = (Button) findViewById(R.id.changeAvatarButton);
        // Add avatar selection dialogue fragment
        avatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoCard.this, insertAvatarDialogueFragment.class);
                startActivity(intent);
            }
        });

    }
}