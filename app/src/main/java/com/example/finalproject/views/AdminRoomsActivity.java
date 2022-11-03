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

import com.example.finalproject.R;
import com.example.finalproject.models.Room;

import java.util.ArrayList;
import java.util.List;

public class AdminRoomsActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private AdminRoomsRecyclerViewAdaptor adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_rooms);

        Intent intent = getIntent();
        String adminEmail = intent.getStringExtra(getString(R.string.Extra_adminEmail));

        Toolbar toolbar = findViewById(R.id.adminRoomsActivityToolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(String.format(getString(R.string.AdminRooms_Toolbar_Tile), adminEmail != null ? adminEmail : ""));

        List<Room> r = new ArrayList<>();
        r.add(new Room("H-404", "Hall Building", "0/0", "test@mail.com"));
        r.add(new Room("FB-C080", "Fauxbourg Building OMG OMG OMG", "0/0", "test@mail.com"));
        r.add(new Room("LB-200", "Library Building", "0/0", "test@mail.com"));
        setupRecyclerView(r);
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
            return true;
        }
        else if (id == R.id.action_remove_room) {
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
}