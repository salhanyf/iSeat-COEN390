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
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.controllers.FirebaseDatabaseHelper;
import com.example.finalproject.models.Room;

import java.util.List;

public class AdminRoomsActivity extends AppCompatActivity {

    private String adminEmail;
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_rooms);

        Intent intent = getIntent();
        adminEmail = intent.getStringExtra(getString(R.string.Extra_adminEmail));

        Toolbar toolbar = findViewById(R.id.adminRoomsActivityToolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(String.format("%s%s", adminEmail != null ? adminEmail : "", getString(R.string.AdminRooms_Toolbar_Tile)));

        recycler = findViewById(R.id.adminRoomsActivityRecyclerView);

        new FirebaseDatabaseHelper().getAdminRooms(adminEmail, new UpdateAdminsRoomsRecyclerView());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin_rooms, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_room) {
            new AddRoomDialogFragment(adminEmail).show(getSupportFragmentManager(), "AddRoomDialogFragment");
            return true;
        }
        else if (id == R.id.action_remove_room) {
            // TODO: remove room dialog fragment
            Toast.makeText(this, "Clicked on Remove Room for admin: " + adminEmail, Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView(List<Room> rooms) {
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(new AdminRoomsRecyclerViewAdaptor(rooms, getSupportFragmentManager()));
    }

    private class UpdateAdminsRoomsRecyclerView implements FirebaseDatabaseHelper.DataStatusRoom {
        @Override
        public void DataIsLoaded(List<Room> rooms) {
            setupRecyclerView(rooms);
        }
    }
}