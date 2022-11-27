package com.example.finalproject.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;


import com.example.finalproject.R;
import com.example.finalproject.views.Cards.FavoriteRoomCard;
import com.example.finalproject.views.Cards.FriendCard;
import com.example.finalproject.views.Cards.HistoryCard;
import com.example.finalproject.views.Cards.UserInfoCard;
import com.example.finalproject.views.Settings.SettingsActivity;

public class UserProfileActivity extends AppCompatActivity {

    private ImageView userInfoImage;
    private ImageView userSettingsImage;
    private ImageView favoriteRoomImage;
    private ImageView friendImage;
    private ImageView historyImage;
    private ImageView deleteAccountImage;

    private CardView userInfoCard;
    private CardView userSettingsCard;
    private CardView favoriteRoomCard;
    private CardView friendCard;
    private CardView historyCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Toolbar toolbar = findViewById(R.id.profileActivityToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        userInfoCard = findViewById(R.id.cardViewUser);
//        userSettingsCard = findViewById(R.id.settingsImage);
//        favoriteRoomCard = findViewById(R.id.favoriteRoomImage);
//        friendCard = findViewById(R.id.friendImage);
//        historyCard = findViewById(R.id.historyImage);


        // Go to user info activity
        userInfoCard.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, UserInfoCard.class);
            startActivity(intent);
        });

        // Go to user settings activity
        userSettingsImage.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        // Go to favorite room activity
        favoriteRoomImage.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, FavoriteRoomCard.class);
            startActivity(intent);
        });

        // Go to friend activity
        friendImage.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, FriendCard.class);
            startActivity(intent);
        });

        // Go to history activity
        historyImage.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, HistoryCard.class);
            startActivity(intent);
        });

        // Go to delete account popup

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}