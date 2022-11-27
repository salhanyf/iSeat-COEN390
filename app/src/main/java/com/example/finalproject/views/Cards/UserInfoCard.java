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

public class UserInfoCard extends AppCompatActivity {

    private Button avatarButton;
    private SharedPreferences sharedPreferences;
    private ImageView chosenAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_card);


        this.sharedPreferences = getSharedPreferences("profile_Shared_Pref", MODE_PRIVATE);
        chosenAvatar = findViewById(R.id.shownAvatar);
        updateImage();

        Toolbar toolbar = findViewById(R.id.userToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        avatarButton = (Button) findViewById(R.id.changeAvatarButton);

        // Add avatar selection dialogue fragment
        avatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertAvatarDialogueFragment avatarDialogueFragment = new insertAvatarDialogueFragment();
                avatarDialogueFragment.show(getSupportFragmentManager(), "avatarDialogueFragment");
            }
        });

    }


    public void updateImage(){
        int imageID = sharedPreferences.getInt("imageID", -1);
        if (imageID != -1) {
            int drawableID = 0;
            switch(imageID) {
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
            chosenAvatar.setImageResource(drawableID);
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