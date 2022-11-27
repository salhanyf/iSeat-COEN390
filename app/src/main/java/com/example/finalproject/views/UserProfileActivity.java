package com.example.finalproject.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;


import com.example.finalproject.R;
import com.example.finalproject.views.Cards.User_FavoriteRoomCard;
import com.example.finalproject.views.Cards.User_FriendCard;
import com.example.finalproject.views.Cards.User_HistoryCard;
import com.example.finalproject.views.Cards.User_InfoCard;
import com.example.finalproject.views.Settings.SettingsActivity;

public class UserProfileActivity extends AppCompatActivity {

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

        userInfoCard = findViewById(R.id.cardViewUser);
        userSettingsCard = findViewById(R.id.cardViewSettings);
        favoriteRoomCard = findViewById(R.id.cardViewFavorite);
        friendCard = findViewById(R.id.cardViewFriend);
        historyCard = findViewById(R.id.cardViewHistory);


        // Go to user info activity
        userInfoCard.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, User_InfoCard.class);
            startActivity(intent);
        });

        // Go to user settings activity
        userSettingsCard.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        // Go to favorite room activity
        favoriteRoomCard.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, User_FavoriteRoomCard.class);
            startActivity(intent);
        });

        // Go to friend activity
        friendCard.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, User_FriendCard.class);
            startActivity(intent);
        });

        // Go to history activity
        historyCard.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, User_HistoryCard.class);
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