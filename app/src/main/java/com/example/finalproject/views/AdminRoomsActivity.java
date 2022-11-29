package com.example.finalproject.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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
import com.example.finalproject.models.Sensor;
import com.example.finalproject.views.Registration.WelcomeActivity;
import com.example.finalproject.views.Settings.SettingsActivity;
import com.example.finalproject.views.adaptors.AdminRoomsRecyclerViewAdaptor;
import com.example.finalproject.views.dialogfragments.AddRoomDialogFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminRoomsActivity extends AppCompatActivity {

    private String adminEmail = null;               // email of admin user (to use when get/set firebase data)
    private RecyclerView recycler;                  // the list of admin's rooms
    private AdminRoomsRecyclerViewAdaptor adaptor;  // adaptor that controls the recycler
    private TextView textViewCancel;                // help text that pops up when deleting

    // items in the toolbar menu
    private MenuItem itemAddRoom, itemRemoveRoom, itemDeleteRoom, itemSettings, itemSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_rooms);
        // get the user email
        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            adminEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        // setup UI elements
        setupUI();
        // setup listener to Firebase for admin's rooms
        new FirebaseDatabaseHelper().getAdminRooms(adminEmail, new UpdateAdminsRoomsRecyclerView());
    }

    private void setupUI() {
        // setup custom toolbar for activity
        Toolbar toolbar = findViewById(R.id.room_clicked_tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // setup textview that shows how to cancel delete
        textViewCancel = findViewById(R.id.textViewCancel);
        textViewCancel.setVisibility(View.GONE);
        // find recyclerview and clear adaptor
        recycler = findViewById(R.id.recyclerViewAdminRooms);
        adaptor = null;
    }

    //toolbar items behaviour
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // use menu items from "iseat_admin_menu.xml"
        getMenuInflater().inflate(R.menu.iseat_admin_menu, menu);
        // find items in menu and save
        itemAddRoom = menu.findItem(R.id.action_add_room);
        itemRemoveRoom = menu.findItem(R.id.action_remove_room);
        itemSettings = menu.findItem(R.id.settingsActionButton);
        itemSignOut = menu.findItem(R.id.signOutActionButton);
        itemDeleteRoom = menu.findItem(R.id.action_delete);
        // DELETE action invisible until needed
        itemDeleteRoom.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_add_room :
                // open the Add Room dialog fragment for this admin
                new AddRoomDialogFragment(adminEmail).show(getSupportFragmentManager(), "AddRoomDialogFragment");
                break;
            case R.id.action_remove_room :
                // toggle the delete button and checkboxes to visible
                toggleDelete(true);
                break;

            case R.id.action_delete :
                // get list of selected rooms to delete
                List<Room> rooms = adaptor.getCheckedRooms();
                if (rooms.isEmpty()) {
                    // if list empty, display toast and return
                    Toast.makeText(this, "Please select 1+ rooms to delete.", Toast.LENGTH_SHORT).show();
                    return true;
                }
                // open delete confirmation dialog for selected rooms
                new DeleteDialog(this, rooms).show();
                break;

            case R.id.settingsActionButton:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                break;

            case R.id.signOutActionButton:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "Goodbye", Toast.LENGTH_SHORT).show();
                Intent signOutIntent = new Intent(this, WelcomeActivity.class);
                startActivity(signOutIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // when back button / back swipe is detected
        // if in delete mode, go back to normal (hide checkboxes)
        if (itemDeleteRoom.isVisible()) toggleDelete(false);
            // else normal behavior (go back)
        else super.onBackPressed();
    }

    // toggle room delete mode
    private void toggleDelete(boolean show) {
        // show/hide toolbar menu actions
        itemDeleteRoom.setVisible(show);
        itemAddRoom.setVisible(!show);
        itemRemoveRoom.setVisible(!show);
        itemSettings.setVisible(!show);
        itemSignOut.setVisible(!show);
        // show/hide help message for exiting delete mode
        textViewCancel.setVisibility(show ? View.VISIBLE : View.GONE);
        // if the recyclerview adaptor is set
        if (adaptor != null) {
            // show checkboxes beside rooms if true
            if (show) adaptor.showCheckBoxes();
                // hide checkboxes beside rooms if false
            else adaptor.hideCheckBoxes();
        }
    }

    // sets up the admin's rooms recycler view
    private void setupRecyclerView(List<Room> rooms) {
        recycler.setLayoutManager(new LinearLayoutManager(this));
        // save adaptor to be able to show/hide item checkboxes
        adaptor = new AdminRoomsRecyclerViewAdaptor(rooms, getSupportFragmentManager());
        // init the recyclerview using custom adaptor
        recycler.setAdapter(adaptor);
    }

    // interface for when data is loaded from Firebase
    private class UpdateAdminsRoomsRecyclerView implements FirebaseDatabaseHelper.DataStatusRoom {
        @Override
        public void DataIsLoaded(List<Room> rooms) {
            // simply pass list of rooms to new recyclerview
            setupRecyclerView(rooms);
        }
    }

    // confirmation delete alert dialog for admin rooms
    private class DeleteDialog extends AlertDialog.Builder {
        public DeleteDialog(Context context, List<Room> rooms) {
            super(context);
            // set the title of alert
            setTitle("Delete?");
            // set alert message to list of rooms
            setMessage(toString(rooms));
            // cancel dismisses alert
            setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
            // ok deletes the rooms
            setPositiveButton("Delete", (dialogInterface, i) -> {
                // delete each room from Firebase, exit delete mode and dismiss alert
                for (Room room : rooms) {
                    FirebaseDatabase.getInstance().getReference("rooms").child(room.getKey()).removeValue();
                    unassignSensors(room);
                }
                toggleDelete(false);
                dialogInterface.dismiss();
            });
        }

        // converts the list of rooms to string
        private String toString(List<Room> rooms) {
            String str = "";
            for (Room room : rooms) str = String.format("%s\n%s", str, room);
            return str;
        }

        private void unassignSensors(Room room) {
            FirebaseDatabase.getInstance().getReference("sensors").orderByChild("roomKey").equalTo(room.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        FirebaseDatabase.getInstance().getReference("sensors").child(snap.getKey()).child("roomKey").setValue("");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(), "ERROR Un-assigning Sensors: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}