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
import com.example.finalproject.views.Cards.AdminUserListCard;
import com.example.finalproject.views.Cards.DeleteAccountCard;
import com.example.finalproject.views.Cards.FavoriteRoomCard;
import com.example.finalproject.views.Cards.FriendCard;
import com.example.finalproject.views.Cards.HistoryCard;
import com.example.finalproject.views.Cards.UserInfoCard;
import com.example.finalproject.views.Settings.SettingsActivity;


public class AdminProfileActivity extends AppCompatActivity {

    private ImageView adminSettingsImage;
    private ImageView favoriteRoomImage;
    private ImageView friendImage;
    private ImageView historyImage;
    private ImageView deleteAccountImage;

    private CardView adminInfoCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);


        Toolbar toolbar = findViewById(R.id.appToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adminInfoCard = findViewById(R.id.cardViewUser);

        adminSettingsImage = findViewById(R.id.imageViewSettings);
        favoriteRoomImage = findViewById(R.id.imageViewFavorite);
        friendImage = findViewById(R.id.imageViewFriendsList);
        historyImage = findViewById(R.id.imageViewHistory);
        deleteAccountImage = findViewById(R.id.imageViewDeleteAccount);

        // Go to admin info activity
        adminInfoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminProfileActivity.this, UserInfoCard.class);
                startActivity(intent);
            }
        });

        // Go to admin settings activity
        adminSettingsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminProfileActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });


        favoriteRoomImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminProfileActivity.this, AdminUserListCard.class);
                startActivity(intent);
            }
        });

        // Go to friend activity
        friendImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminProfileActivity.this, FriendCard.class);
                startActivity(intent);
            }
        });

        // Go to history activity
        historyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminProfileActivity.this, HistoryCard.class);
                startActivity(intent);
            }
        });

        // Go to delete account activity
        deleteAccountImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminProfileActivity.this, DeleteAccountCard.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}