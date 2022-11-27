package com.example.finalproject.views.Cards;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_card);

        Toolbar toolbar = findViewById(R.id.userToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

