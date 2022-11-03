package com.example.finalproject.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.finalproject.R;

public class AdminRoomsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_rooms);

        Toolbar toolbar = findViewById(R.id.adminRoomsActivityToolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(String.format(getString(R.string.AdminRooms_Toolbar_Tile), "Samson"));
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
}