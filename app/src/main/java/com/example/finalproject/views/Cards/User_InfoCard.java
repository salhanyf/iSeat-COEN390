package com.example.finalproject.views.Cards;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
    private ImageView chosenUserAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_card);

        this.sharedPreferences = getSharedPreferences("profile_Shared_Pref", MODE_PRIVATE);
        chosenUserAvatar = findViewById(R.id.shownUserAvatar);
        updateImage();

        Toolbar toolbar = findViewById(R.id.userToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("User Info");

        usernameEditText = (EditText) findViewById(R.id.viewUsername);
        avatarButton = (Button) findViewById(R.id.changeUserAvatarButton);
        editButton = (Button) findViewById(R.id.editButton);
        saveButton = (Button) findViewById(R.id.saveButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editButton.setVisibility(View.INVISIBLE);
                saveButton.setVisibility(View.VISIBLE);
                cancelButton.setVisibility(View.VISIBLE);
                avatarButton.setVisibility(View.VISIBLE);

                // Add avatar selection dialogue fragment
                avatarButton.setOnClickListener(v -> {
                    insertAvatarDialogueFragment avatarDialogueFragment = new insertAvatarDialogueFragment(true);
                    avatarDialogueFragment.show(getSupportFragmentManager(), "avatarDialogueFragment");
                });

        TextView email = findViewById(R.id.viewEmail);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        email.setText(mAuth.getCurrentUser().getEmail());

        // go into all the keys under "users" and find the one that matches the current user's email
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String email = snapshot.child("email").getValue(String.class);
                    if (email.equalsIgnoreCase(mAuth.getCurrentUser().getEmail())) {
                        String username = snapshot.child("username").getValue(String.class);
                        TextView usernameView = findViewById(R.id.viewUsername);
                        usernameView.setText(username);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

                //TODO: SAVE THE VALUUES ENTERED - ONLY USERNAME AND AVATAR (EMAIL CANNOT BE EDITED)
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

                //TODO: MAKE SURE NOTHING IS SAVED
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        saveButton.setVisibility(View.INVISIBLE);
                        cancelButton.setVisibility(View.INVISIBLE);
                        avatarButton.setVisibility(View.INVISIBLE);
                        editButton.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
    }

    public void updateImage() {
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}