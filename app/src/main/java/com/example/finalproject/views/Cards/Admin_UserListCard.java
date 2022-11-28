package com.example.finalproject.views.Cards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.finalproject.R;
import com.example.finalproject.controllers.FirebaseDatabaseHelper;
import com.example.finalproject.views.adaptors.UserListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Admin_UserListCard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_list_card);
        new FirebaseDatabaseHelper().readUsers();

        // Back button functionality
        Toolbar toolbar = findViewById(R.id.adminUserListToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Admin Info");

        //get the list of users from the database and display them in a recycler view list
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //get the list of users from the database and display them in a recycler view list
                RecyclerView recyclerView = findViewById(R.id.UserListRec);
                recyclerView.setLayoutManager(new LinearLayoutManager(Admin_UserListCard.this));
                recyclerView.setAdapter(new UserListAdapter(dataSnapshot));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



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