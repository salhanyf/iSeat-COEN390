/*
    File:           AdminProfileActivity.java
    Authors:        Adnan Saab          #40075504
                    Samson Kaller       #40136815
                    Farah Salhany       #40074803
                    Shahin Khalkhali    #40057384
                    Shayan Khalkhali    #40059491
                    Marwan Al-Ghaziri   #40126554
    Description:    This class controls items on the Admin Profile activity. This is the page with
                    cards/tiles that pops up with buttons to go to other activities for the Admin.
*/
package com.example.finalproject.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.finalproject.R;
import com.example.finalproject.controllers.FirebaseDatabaseHelper;
import com.example.finalproject.views.Cards.Admin_HistoryCard;
import com.example.finalproject.views.Cards.Admin_InfoCard;
import com.example.finalproject.views.Cards.Admin_UserListCard;
import com.example.finalproject.views.Registration.WelcomeActivity;
import com.example.finalproject.views.Settings.SettingsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminProfileActivity extends AppCompatActivity {

    private CardView adminInfoCard, adminManageRoomsCard, adminUserListCard, adminHistoryCard, adminSettingsCard, adminDeleteAccountCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        Toolbar toolbar = findViewById(R.id.appToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adminInfoCard = findViewById(R.id.cardViewAdminInfo);
        adminManageRoomsCard = findViewById(R.id.cardViewManageRooms);
        adminUserListCard = findViewById(R.id.cardViewUserList);
        //adminHistoryCard = findViewById(R.id.cardViewAdminHistory);
        adminSettingsCard = findViewById(R.id.cardViewAdminSettings);
        adminDeleteAccountCard = findViewById(R.id.cardViewDeleteAdminAccount);

        // Go to user info activity
        adminInfoCard.setOnClickListener(view -> {
            Intent intent = new Intent(AdminProfileActivity.this, Admin_InfoCard.class);
            startActivity(intent);
        });

        //manage rooms card
        adminManageRoomsCard.setOnClickListener(view -> {
            Intent intent = new Intent(AdminProfileActivity.this, AdminRoomsActivity.class);
            startActivity(intent);
        });

        //user list card
        adminUserListCard.setOnClickListener(view -> startActivity(new Intent(AdminProfileActivity.this, Admin_UserListCard.class)));

        //history card
//        adminHistoryCard.setOnClickListener(view -> {
//            Intent intent = new Intent(AdminProfileActivity.this, Admin_HistoryCard.class);
//            startActivity(intent);
//        });

        //settings card
        adminSettingsCard.setOnClickListener(view -> startActivity(new Intent(AdminProfileActivity.this, SettingsActivity.class)));

        //deleting account card
        adminDeleteAccountCard.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(AdminProfileActivity.this);
            builder.setTitle("Are you sure!").setMessage("Note: you will not be able to recover your account");
            builder.setCancelable(false);

            builder.setPositiveButton("Yes, delete", (dialog, which) -> {
                // if user click yes button then the account is deleted and user is redirected to Welcome activity
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseDatabaseHelper.deleteUser(user.getEmail());
                user.delete()   // delete user from firebase
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(AdminProfileActivity.this, "Account deleted", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AdminProfileActivity.this, WelcomeActivity.class);
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
}