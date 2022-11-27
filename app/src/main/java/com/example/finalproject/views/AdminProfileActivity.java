package com.example.finalproject.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.views.Registration.WelcomeActivity;
import com.example.finalproject.views.Settings.SettingsActivity;
import com.google.firebase.auth.FirebaseAuth;
public class AdminProfileActivity extends AppCompatActivity {

    private CardView adminInfoCard, manageRoomsCard, userListCard, adminHistoryCard, adminSettingsCard, adminDeleteAccountCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        Toolbar toolbar = findViewById(R.id.appToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adminInfoCard = findViewById(R.id.cardViewAdminInfo);
        manageRoomsCard = findViewById(R.id.cardViewManageRooms);
        userListCard = findViewById(R.id.cardViewUserList);
        adminHistoryCard = findViewById(R.id.cardViewAdminHistory);
        adminSettingsCard = findViewById(R.id.cardViewAdminSettings);
        adminDeleteAccountCard = findViewById(R.id.cardViewDeleteAdminAccount);

        //admin info card
        adminInfoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //manage rooms card
        manageRoomsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminProfileActivity.this, AdminRoomsActivity.class));
            }
        });

        //user list card
        userListCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //history card
        adminHistoryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //settings card
        adminSettingsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminProfileActivity.this, SettingsActivity.class));
            }
        });

        //deleting account card
        adminDeleteAccountCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminProfileActivity.this);
                builder.setTitle("Are you sure!").setMessage("Note: you will not be able to recover your account");
                builder.setCancelable(false);

                builder.setPositiveButton("Yes, delete", (DialogInterface.OnClickListener) (dialog, which) -> {
                    // if user click yes button then the account is deleted and user is redirected to Welcome activity
                    //TODO: DELETE ACCOUNT FROM FIREBASE
                    finish();
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(AdminProfileActivity.this, WelcomeActivity.class));
                    Toast.makeText(AdminProfileActivity.this, "Goodbye!", Toast.LENGTH_SHORT).show();
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
}