package com.example.finalproject.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.controllers.FirebaseDatabaseHelper;
import com.example.finalproject.models.Room;
import com.example.finalproject.views.Registration.WelcomeActivity;
import com.example.finalproject.views.Settings.SettingsActivity;
import com.example.finalproject.views.adaptors.RoomListRecyclerViewAdaptor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RoomListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        Toolbar toolbar = findViewById(R.id.appToolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.progressBarRecyclerView);
        mRecyclerView = findViewById(R.id.Room_RecyclerViewID);
        new FirebaseDatabaseHelper().readRooms(new UpdateRoomsRecyclerView());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, WelcomeActivity.class));
        }
    }

    private class UpdateRoomsRecyclerView implements FirebaseDatabaseHelper.DataStatusRoom {
        @Override
        public void DataIsLoaded(List<Room> rooms) {
            if (progressBar.getVisibility() != View.GONE) progressBar.setVisibility(View.GONE);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(RoomListActivity.this));
            mRecyclerView.setAdapter(new RoomListRecyclerViewAdaptor(RoomListActivity.this, rooms));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // use menu items from "iseat_user_menu.xml"
        getMenuInflater().inflate(R.menu.iseat_user_menu, menu);
        MenuItem itemManageRooms = menu.findItem(R.id.manageRoomActionButton);
        itemManageRooms.setVisible(false);

        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            String email = auth.getCurrentUser().getEmail();
            FirebaseDatabase.getInstance().getReference("admins").orderByValue().equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        String e = snap.getValue(String.class);
                        if (e.equals(email)) {
                            itemManageRooms.setVisible(true);
                            break;
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(RoomListActivity.this, "Failure during get admin", Toast.LENGTH_SHORT).show();
                }
            });
        }

        return super.onCreateOptionsMenu(menu);
    }
    // behaviour of toolbar items
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.searchActionButton:
                Toast.makeText(this, "Search Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.refreshActionButton:
                Toast.makeText(this, "Refresh Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settingsActionButton:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                break;
            case R.id.signOutActionButton:
                //TODO: signing out user
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "Goodbye", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, WelcomeActivity.class));
                break;
            case R.id.manageRoomActionButton:
                startActivity(new Intent(this, AdminRoomsActivity.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}