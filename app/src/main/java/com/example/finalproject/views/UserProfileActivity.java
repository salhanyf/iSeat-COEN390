package com.example.finalproject.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;


import com.example.finalproject.R;
import com.example.finalproject.views.Cards.FavoriteRoomCard;
import com.example.finalproject.views.Cards.FriendCard;
import com.example.finalproject.views.Cards.HistoryCard;
import com.example.finalproject.views.Cards.UserInfoCard;
import com.example.finalproject.views.Registration.WelcomeActivity;
import com.example.finalproject.views.Settings.SettingsActivity;
import com.google.firebase.auth.FirebaseAuth;

public class UserProfileActivity extends AppCompatActivity {

    private CardView userInfoCard, favoriteRoomsCard, friendsListCard, userHistoryCard, userSettingsCard, userDeleteAccountCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Toolbar toolbar = findViewById(R.id.appToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userInfoCard = findViewById(R.id.cardViewUserInfo);
        favoriteRoomsCard = findViewById(R.id.cardViewFavoriteRooms);
        friendsListCard = findViewById(R.id.cardViewFriendsList);
        userHistoryCard = findViewById(R.id.cardViewUserHistory);
        userSettingsCard = findViewById(R.id.cardViewUserSettings);
        userDeleteAccountCard = findViewById(R.id.cardViewDeleteUserAccount);

        // User Info card
        userInfoCard.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, UserInfoCard.class);
            startActivity(intent);
        });

        // User Favorite Rooms card
        favoriteRoomsCard.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, FavoriteRoomCard.class);
            startActivity(intent);
        });

        // User Friends List card
        friendsListCard.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, FriendCard.class);
            startActivity(intent);
        });

        // User History card
        userHistoryCard.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, HistoryCard.class);
            startActivity(intent);
        });

        // User settings card
        userSettingsCard.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        // User Delete Account card
        userDeleteAccountCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this);
                builder.setTitle("Are you sure!").setMessage("Note: you will not be able to recover your account");
                builder.setCancelable(false);

                builder.setPositiveButton("Yes, delete", (DialogInterface.OnClickListener) (dialog, which) -> {
                    // if user click yes button then the account is deleted and user is redirected to Welcome activity
                    //TODO: DELETE ACCOUNT FROM FIREBASE
                    finish();
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(UserProfileActivity.this, WelcomeActivity.class));
                    Toast.makeText(UserProfileActivity.this, "Goodbye!", Toast.LENGTH_SHORT).show();
                });
                builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                    // If user click no button then dialog box is canceled
                    dialog.cancel();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
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
