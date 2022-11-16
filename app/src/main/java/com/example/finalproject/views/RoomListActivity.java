package com.example.finalproject.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.controllers.FirebaseDatabaseHelper;
import com.example.finalproject.models.Room;
import com.example.finalproject.views.adaptors.RoomListRecyclerViewAdaptor;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    //toolbar menu behaviour
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
//            case R.id.action_search:
//                Toast.makeText(this, "Search Clicked", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.action_refresh:
//                Toast.makeText(this, "Refresh Clicked", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.action_settings:
//                Toast.makeText(this, "Settings Clicked", Toast.LENGTH_SHORT).show();
//                break;
            case R.id.action_sign_out:
                //TODO: signing out user
//                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//                    String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
//                    FirebaseAuth.getInstance().signOut();
//                    Toast.makeText(this, "User: " + email + " logged off.", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(this, LoginActivity.class));
//                }
                Toast.makeText(this, "Goodbye", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}