/*
    File:           UserProfileActivity.java
    Authors:        Adnan Saab          #40075504
                    Samson Kaller       #40136815
                    Farah Salhany       #40074803
                    Shahin Khalkhali    #40057384
                    Shayan Khalkhali    #40059491
                    Marwan Al-Ghaziri   #40126554
    Description:    This class controls items on the User Profile activity. This is the page with
                    cards/tiles that pops up with buttons to go to other activities for the User
                    (not admin).
*/
package com.example.finalproject.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.finalproject.R;
import com.example.finalproject.controllers.FirebaseDatabaseHelper;
import com.example.finalproject.views.Cards.User_FavoriteRoomCard;
import com.example.finalproject.views.Cards.User_FriendCard;
import com.example.finalproject.views.Cards.User_InfoCard;
import com.example.finalproject.views.Registration.WelcomeActivity;
import com.example.finalproject.views.Settings.SettingsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserProfileActivity extends AppCompatActivity {

    private CardView userInfoCard, userFavoriteRoomsCard, userFriendsListCard, userSettingsCard, userDeleteAccountCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Toolbar toolbar = findViewById(R.id.profileActivityToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userInfoCard = findViewById(R.id.cardViewUser);
        userFavoriteRoomsCard = findViewById(R.id.cardViewFavorite);
        userFriendsListCard = findViewById(R.id.cardViewFriend);
        userSettingsCard = findViewById(R.id.cardViewSettings);
        userDeleteAccountCard = findViewById(R.id.cardViewDeleteUserAccount);

        // Go to user info activity
        userInfoCard.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, User_InfoCard.class);
            startActivity(intent);
        });

        // Go to user favorite rooms activity
        userFavoriteRoomsCard.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, User_FavoriteRoomCard.class);
            startActivity(intent);
        });

        // Go to user friends list activity
        userFriendsListCard.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, User_FriendCard.class);
            startActivity(intent);
        });

        // Go to user settings activity
        userSettingsCard.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        // User Delete Account card
        userDeleteAccountCard.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this);
            builder.setTitle("Are you sure!").setMessage("Note: you will not be able to recover your account");
            builder.setCancelable(false);

            builder.setPositiveButton("Yes, delete", (dialog, which) -> {
                // if user click yes button then the account is deleted and user is redirected to Welcome activity
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseDatabaseHelper.deleteUser(user.getEmail());
                user.delete()   // delete user from firebase authentication
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(UserProfileActivity.this, "Account deleted", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UserProfileActivity.this, WelcomeActivity.class);
                                startActivity(intent);
                            }
                        });
            });
            builder.setNegativeButton("No", (dialog, which) -> {
                // If user click no button then dialog box is canceled
                dialog.cancel();
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
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