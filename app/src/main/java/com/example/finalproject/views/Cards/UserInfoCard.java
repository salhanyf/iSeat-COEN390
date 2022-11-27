package com.example.finalproject.views.Cards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.SharedPreferences;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class UserInfoCard extends AppCompatActivity {

    private Button avatarButton;
    private SharedPreferences sharedPreferences;
    private ImageView chosenAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_card);

        this.sharedPreferences = getSharedPreferences("profile_Shared_Pref", MODE_PRIVATE);
        chosenAvatar = findViewById(R.id.shownAvatar);
        updateImage();

        Toolbar toolbar = findViewById(R.id.userToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("User Info");

        avatarButton = (Button) findViewById(R.id.changeAvatarButton);

        // Add avatar selection dialogue fragment
        avatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertAvatarDialogueFragment avatarDialogueFragment = new insertAvatarDialogueFragment();
                avatarDialogueFragment.show(getSupportFragmentManager(), "avatarDialogueFragment");
            }
        });

        TextView email = findViewById(R.id.viewEmail);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        email.setText(mAuth.getCurrentUser().getEmail());

        // go into all the keys under "users" and find the one that matches the current user's email
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String email = snapshot.child("email").getValue(String.class);
                    Log.d("!!!!!!!!email", email);
                    if (email.equals(mAuth.getCurrentUser().getEmail())) {
                        String username = snapshot.child("username").getValue(String.class);
                        TextView usernameView = findViewById(R.id.viewUsername);
                        usernameView.setText(username);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void updateImage(){
        int imageID = sharedPreferences.getInt("imageID", -1);
        if (imageID != -1) {
            int drawableID = 0;
            switch(imageID) {
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
            chosenAvatar.setImageResource(drawableID);
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

