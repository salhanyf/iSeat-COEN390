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
    private AdminRoomsRecyclerViewAdaptor adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_rooms);

        Intent intent = getIntent();
        adminEmail = intent.getStringExtra(getString(R.string.Extra_adminEmail));

        Toolbar toolbar = findViewById(R.id.adminRoomsActivityToolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(String.format(getString(R.string.AdminRooms_Toolbar_Tile), adminEmail != null ? adminEmail : ""));
        
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
            Toast.makeText(this, "Clicked on Add Room for admin: " + adminEmail, Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id == R.id.action_remove_room) {
            Toast.makeText(this, "Clicked on Remove Room for admin: " + adminEmail, Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView(List<Room> rooms) {
        adaptor = new AdminRoomsRecyclerViewAdaptor(rooms);
        recycler = findViewById(R.id.adminRoomsActivityRecyclerView);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adaptor);
    }

    private class UpdateAdminsRoomsRecyclerView implements FirebaseDatabaseHelper.DataStatusRoom {
        @Override
        public void DataIsLoaded(List<Room> rooms, List<String> keys) {
            setupRecyclerView(rooms);
        }
    }
}