/*
    File:           User_InfoCard.java
    Authors:        Adnan Saab          #40075504
                    Samson Kaller       #40136815
                    Farah Salhany       #40074803
                    Shahin Khalkhali    #40057384
                    Shayan Khalkhali    #40059491
                    Marwan Al-Ghaziri   #40126554
    Description:    This class controls items on the card activity for User (not admin) Info
                    (changing username and display avatar pic for admins).
*/
package com.example.finalproject.views.Cards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.SharedPreferences;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalproject.R;
import com.example.finalproject.views.dialogfragments.insertAvatarDialogueFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class User_InfoCard extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private Button avatarButton, editButton, saveButton, cancelButton;
    private EditText usernameEditText;
    private TextView userEmail;
    private ImageView chosenUserAvatar;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_card);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        this.sharedPreferences = getSharedPreferences("profile_Shared_Pref", MODE_PRIVATE);
        chosenUserAvatar = findViewById(R.id.shownUserAvatar);
        updateImage();

        // Back button functionality
        Toolbar toolbar = findViewById(R.id.userToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("User Info");

        usernameEditText = (EditText) findViewById(R.id.viewUsername);
        usernameEditText.setEnabled(false);
        userEmail = (TextView) findViewById(R.id.viewEmail);
        avatarButton = (Button) findViewById(R.id.changeUserAvatarButton);
        editButton = (Button) findViewById(R.id.editButton);
        saveButton = (Button) findViewById(R.id.saveButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);

        // set current user email
        String email = mAuth.getCurrentUser().getEmail();
        userEmail.setText(email);

        // go into all the keys under "users" and find the one that matches the current user's email
        // set current user username
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String email = snapshot.child("email").getValue(String.class);
                    if (email.equalsIgnoreCase(mAuth.getCurrentUser().getEmail())) {
                        username = snapshot.child("username").getValue(String.class);
                        usernameEditText.setText(username);
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usernameEditText.setEnabled(true);
                editButton.setVisibility(View.INVISIBLE);
                saveButton.setVisibility(View.VISIBLE);
                cancelButton.setVisibility(View.VISIBLE);
                avatarButton.setVisibility(View.VISIBLE);

                // Add avatar selection dialogue fragment
                avatarButton.setOnClickListener(v -> {
                    insertAvatarDialogueFragment avatarDialogueFragment = new insertAvatarDialogueFragment(true);
                    avatarDialogueFragment.show(getSupportFragmentManager(), "avatarDialogueFragment");
                });

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseDatabase.getInstance().getReference("users").orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot snap : snapshot.getChildren()) {
                                    FirebaseDatabase.getInstance().getReference("users").child(snap.getKey()).child("username").setValue(usernameEditText.getText().toString());
                                    break;
                                }
                                usernameEditText.setEnabled(false);
                                saveButton.setVisibility(View.INVISIBLE);
                                cancelButton.setVisibility(View.INVISIBLE);
                                avatarButton.setVisibility(View.INVISIBLE);
                                editButton.setVisibility(View.VISIBLE);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        usernameEditText.setEnabled(false);
                        saveButton.setVisibility(View.INVISIBLE);
                        cancelButton.setVisibility(View.INVISIBLE);
                        avatarButton.setVisibility(View.INVISIBLE);
                        editButton.setVisibility(View.VISIBLE);

                        usernameEditText.setText(username);
                    }
                });
            }
        });
    }

    public void updateImage(){
        int userImageID = sharedPreferences.getInt("ImageID", -1);
        if (userImageID != -1) {
            int drawableID = 0;
            switch(userImageID) {
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
            chosenUserAvatar.setImageResource(drawableID);
        }
    }

    // Back button functionality
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}