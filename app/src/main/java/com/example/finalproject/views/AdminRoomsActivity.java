package com.example.finalproject.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.controllers.FirebaseDatabaseHelper;
import com.example.finalproject.models.Room;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AdminRoomsActivity extends AppCompatActivity {

    private String adminEmail;
    private RecyclerView recycler;
    private AdminRoomsRecyclerViewAdaptor adaptor;
    private TextView textViewCancel;
    private MenuItem itemAddRoom, itemRemoveRoom, itemDeleteRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_rooms);

        Intent intent = getIntent();
        adminEmail = intent.getStringExtra(getString(R.string.Extra_adminEmail));

        Toolbar toolbar = findViewById(R.id.toolbarAdminRooms);
        setSupportActionBar(toolbar);
        toolbar.setTitle(String.format("%s%s", adminEmail != null ? adminEmail : "", getString(R.string.AdminRooms_Toolbar_Tile)));

        textViewCancel = findViewById(R.id.textViewCancel);
        textViewCancel.setVisibility(View.GONE);

        recycler = findViewById(R.id.recyclerViewAdminRooms);
        adaptor = null;
        new FirebaseDatabaseHelper().getAdminRooms(adminEmail, new UpdateAdminsRoomsRecyclerView());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin_rooms, menu);
        itemAddRoom = menu.findItem(R.id.action_add_room);
        itemRemoveRoom = menu.findItem(R.id.action_remove_room);
        itemDeleteRoom = menu.findItem(R.id.action_delete);
        itemDeleteRoom.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (itemDeleteRoom.isVisible())
            toggleDelete(false);
        else super.onBackPressed();
    }

    private void toggleDelete(boolean del) {
        if (del) {
            itemDeleteRoom.setVisible(true);
            itemAddRoom.setVisible(false);
            itemRemoveRoom.setVisible(false);
            if (adaptor != null) adaptor.showCheckBoxes();
            textViewCancel.setVisibility(View.VISIBLE);
        } else {
            itemDeleteRoom.setVisible(false);
            itemAddRoom.setVisible(true);
            itemRemoveRoom.setVisible(true);
            if (adaptor != null) {
                adaptor.hideCheckBoxes();
                adaptor.uncheckBoxes();
            }
            textViewCancel.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_room) {
            new AddRoomDialogFragment(adminEmail).show(getSupportFragmentManager(), "AddRoomDialogFragment");
            return true;
        }
        else if (id == R.id.action_remove_room) {
            toggleDelete(true);
            return true;
        }
        else if (id == R.id.action_delete) {
            List<Room> rooms = adaptor.getCheckedRooms();

            if (rooms.isEmpty()) {
                Toast.makeText(this, "Please check 1+ rooms to delete.", Toast.LENGTH_SHORT).show();
                return true;
            }

            String s = "";
            for (Room r : rooms)
                s += "\n" + r;

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Delete?");
            alert.setMessage(s);
            alert.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
            alert.setPositiveButton("Delete", (dialogInterface, i) -> {
                for (Room r : rooms)
                    FirebaseDatabase.getInstance().getReference("rooms").child(r.getKey()).removeValue();
                toggleDelete(false);
                dialogInterface.dismiss();
            });
            alert.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView(List<Room> rooms) {
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adaptor = new AdminRoomsRecyclerViewAdaptor(rooms, getSupportFragmentManager());
        recycler.setAdapter(adaptor);
    }

    private class UpdateAdminsRoomsRecyclerView implements FirebaseDatabaseHelper.DataStatusRoom {
        @Override
        public void DataIsLoaded(List<Room> rooms) {
            setupRecyclerView(rooms);
        }
    }
}