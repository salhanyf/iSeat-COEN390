package com.example.finalproject.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;
import com.example.finalproject.views.Cards.DeleteAccountCard;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        userInfoImage = (ImageView) findViewById(R.id.imageViewUser);
        userSettingsImage = (ImageView) findViewById(R.id.imageViewSettings);
        favoriteRoomImage = (ImageView) findViewById(R.id.imageViewFavorite);
        friendImage = (ImageView) findViewById(R.id.imageViewFriendsList);
        historyImage = (ImageView) findViewById(R.id.imageViewHistory);
        deleteAccountImage = (ImageView) findViewById(R.id.imageViewDeleteAccount);

        // Go to user info activity
        userInfoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, UserInfoCard.class);
                startActivity(intent);
            }
        });

        // Go to user settings activity
        userSettingsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        // Go to favorite room activity
        favoriteRoomImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, FavoriteRoomCard.class);
                startActivity(intent);
            }
        });

        // Go to friend activity
        friendImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, FriendCard.class);
                startActivity(intent);
            }
        });

        // Go to history activity
        historyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, HistoryCard.class);
                startActivity(intent);
            }
        });

        // Go to delete account activity
        deleteAccountImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, DeleteAccountCard.class);
                startActivity(intent);
            }
        });
    }

}