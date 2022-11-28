package com.example.finalproject.views.Cards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.SharedPreferences;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.finalproject.R;
import com.example.finalproject.views.dialogfragments.insertAvatarDialogueFragment;

public class Admin_InfoCard extends AppCompatActivity {

    private Button avatarButton;
    private SharedPreferences sharedPreferences;
    private ImageView chosenAdminAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_info_card);

        this.sharedPreferences = getSharedPreferences("profile_Shared_Pref", MODE_PRIVATE);
        chosenAdminAvatar = findViewById(R.id.shownAdminAvatar);
        updateImage();

        Toolbar toolbar = findViewById(R.id.adminToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Admin Info");

        avatarButton = findViewById(R.id.changeAdminAvatarButton);

        // Add avatar selection dialogue fragment
        avatarButton.setOnClickListener(v -> {
            insertAvatarDialogueFragment avatarDialogueFragment = new insertAvatarDialogueFragment(false);
            avatarDialogueFragment.show(getSupportFragmentManager(), "avatarDialogueFragment");
        });

    }
    
    public void updateImage(){
        int adminImageID = sharedPreferences.getInt("ImageID", -1);
        if (adminImageID != -1) {
            int drawableID = 0;
            switch(adminImageID) {
                case 1:
                    drawableID = R.drawable.avatars1;
                    break;
                case 2:
                    drawableID = R.drawable.avatars2;
                    break;
                case 3:
                    drawableID = R.drawable.avatars3;
                    break;
                case 4:
                    drawableID = R.drawable.avatars4;
                    break;
                case 5:
                    drawableID = R.drawable.avatars5;
                    break;
                case 6:
                    drawableID = R.drawable.avatars6;
                    break;
                case 7:
                    drawableID = R.drawable.avatars7;
                    break;
                case 8:
                    drawableID = R.drawable.avatars8;
                    break;
                case 9:
                    drawableID = R.drawable.avatars9;
                    break;
                default:
                    break;
            }
            chosenAdminAvatar.setImageResource(drawableID);
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