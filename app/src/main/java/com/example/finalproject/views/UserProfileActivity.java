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
import com.example.finalproject.views.Cards.User_FavoriteRoomCard;
import com.example.finalproject.views.Cards.User_FriendCard;
import com.example.finalproject.views.Cards.User_HistoryCard;
import com.example.finalproject.views.Cards.User_InfoCard;
import com.example.finalproject.views.Registration.WelcomeActivity;
import com.example.finalproject.views.Settings.SettingsActivity;
import com.google.firebase.auth.FirebaseAuth;

public class UserProfileActivity extends AppCompatActivity {

    private CardView userInfoCard;
    private CardView userSettingsCard;
    private CardView favoriteRoomCard;
    private CardView friendCard;
    private CardView historyCard;
    private CardView userDeleteAccountCard;

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
        userDeleteAccountCard = findViewById(R.id.cardViewDeleteUserAccount);



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