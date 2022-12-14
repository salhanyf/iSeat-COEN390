/*
    File:           RoomListActivity.java
    Authors:        Adnan Saab          #40075504
                    Samson Kaller       #40136815
                    Farah Salhany       #40074803
                    Shahin Khalkhali    #40057384
                    Shayan Khalkhali    #40059491
                    Marwan Al-Ghaziri   #40126554
    Description:    This class controls items on the Room List activity. This is the main activity
                    for the app. If a user has already logged in and opens the app, this page is
                    displayed. If the user is not logged in, they will be sent to the welcome page
                    to register/sign in before they can access this activity. Displays a list of rooms
                    with location as well as the amount of available seating in each room.
*/
package com.example.finalproject.views;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RoomListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        toolbar = findViewById(R.id.room_clicked_tool_bar);
        setSupportActionBar(toolbar);
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
        //checking app appearance to apply the appropriate theme
        final String[] appearanceValues = getResources().getStringArray(R.array.appearance_values);
        String pref = PreferenceManager.getDefaultSharedPreferences(this)
                .getString("settingsAppAppearance", "MODE_NIGHT_FOLLOW_SYSTEM");
        if (pref.equals(appearanceValues[0]))
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        if (pref.equals(appearanceValues[1]))
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (pref.equals(appearanceValues[2]))
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.iseat_user_menu, menu);

        MenuItem itemManageRooms = menu.findItem(R.id.manageRoomActionButton);
        itemManageRooms.setVisible(false);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            String email = auth.getCurrentUser().getEmail();
            FirebaseDatabase.getInstance().getReference("users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        Log.w("TAG", "onDataChange: " + snap);
                        if (snap.child("isAdmin").getValue().toString().equals("true") && snap.child("email").getValue().toString().equalsIgnoreCase(email)) {
                            itemManageRooms.setVisible(true);
                            //add an admin profile button to the toolbar

                            toolbar.setNavigationIcon(R.drawable.ic_account_admin_settings);
                            toolbar.setNavigationOnClickListener(v -> {
                                Intent intent = new Intent(RoomListActivity.this, AdminProfileActivity.class);
                                startActivity(intent);
                            });
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
        //add a user profile button to the toolbar

        toolbar.setNavigationIcon(R.drawable.ic_account_circle);
        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(RoomListActivity.this, UserProfileActivity.class);
            startActivity(intent);
        });

        return super.onCreateOptionsMenu(menu);
    }

    // behaviour of toolbar items
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refreshActionButton:
                new FirebaseDatabaseHelper().readRooms(new UpdateRoomsRecyclerView());
                Toast.makeText(this, "List is up-to-date", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settingsActionButton:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                break;
            case R.id.signOutActionButton:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "Goodbye!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, WelcomeActivity.class));
                break;
            case R.id.manageRoomActionButton:
                startActivity(new Intent(this, AdminRoomsActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private class UpdateRoomsRecyclerView implements FirebaseDatabaseHelper.DataStatusRoom {
        @Override
        public void DataIsLoaded(List<Room> rooms) {
            Collections.sort(rooms, (left, right) -> {
                if (left.getLocation().equals(right.getLocation()))
                    return left.getName().compareTo(right.getName());
                return left.getLocation().compareTo(right.getLocation());
            });

            String email = "";

            if (FirebaseAuth.getInstance().getCurrentUser() != null)
             email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

            if (progressBar.getVisibility() != View.GONE) progressBar.setVisibility(View.GONE);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(RoomListActivity.this));
            mRecyclerView.setAdapter(new RoomListRecyclerViewAdaptor(RoomListActivity.this, rooms, email));
        }
    }
}