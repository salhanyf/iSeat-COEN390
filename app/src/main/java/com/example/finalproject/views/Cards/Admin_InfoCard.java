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

public class Admin_InfoCard extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private Button avatarButton, editButton, saveButton, cancelButton;
    private EditText usernameEditText;
    private TextView adminEmail;
    private ImageView chosenAdminAvatar;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_info_card);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        this.sharedPreferences = getSharedPreferences("profile_Shared_Pref", MODE_PRIVATE);
        chosenAdminAvatar = findViewById(R.id.shownAdminAvatar);
        updateImage();

        // Back button functionality
        Toolbar toolbar = findViewById(R.id.adminToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Admin Info");

        usernameEditText = (EditText) findViewById(R.id.viewAdminUsername);
        usernameEditText.setEnabled(false);
        adminEmail = (TextView) findViewById(R.id.viewAdminEmail);
        avatarButton = findViewById(R.id.changeAdminAvatarButton);
        editButton = (Button) findViewById(R.id.editButton);
        saveButton = (Button) findViewById(R.id.saveButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);

        // set current user email
        String email = mAuth.getCurrentUser().getEmail();
        adminEmail.setText(email);

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
                    insertAvatarDialogueFragment avatarDialogueFragment = new insertAvatarDialogueFragment(false);
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
        int adminImageID = sharedPreferences.getInt("ImageID", -1);
        if (adminImageID != -1) {
            int drawableID = 0;
            switch(adminImageID) {
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
            chosenAdminAvatar.setImageResource(drawableID);
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