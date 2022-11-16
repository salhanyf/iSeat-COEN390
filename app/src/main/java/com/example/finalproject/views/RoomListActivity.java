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

import java.util.List;

public class RoomListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        toolbar = findViewById(R.id.appToolbar);
        setSupportActionBar(toolbar);

        progressBar = findViewById(R.id.progressBarRecyclerView);
        mRecyclerView = findViewById(R.id.Room_RecyclerViewID);
        new FirebaseDatabaseHelper().readRooms(new UpdateRoomsRecyclerView());
    }

    private class UpdateRoomsRecyclerView implements FirebaseDatabaseHelper.DataStatusRoom {
        @Override
        public void DataIsLoaded(List<Room> rooms) {
            if (progressBar.getVisibility() != View.GONE) progressBar.setVisibility(View.GONE);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(RoomListActivity.this));
            mRecyclerView.setAdapter(new RoomListRecyclerViewAdaptor(RoomListActivity.this, rooms));
        }
    }

    //toolbar items behaviour
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // use menu items from "iseat_user_menu.xml"
        getMenuInflater().inflate(R.menu.iseat_user_menu, menu);
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
                Toast.makeText(this, "Goodbye", Toast.LENGTH_SHORT).show();
                Intent signOutIntent = new Intent(this, WelcomeActivity.class);
                startActivity(signOutIntent);
//                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//                    String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
//                    FirebaseAuth.getInstance().signOut();
//                    Toast.makeText(this, "User: " + email + " logged off.", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(this, LoginActivity.class));
//                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}