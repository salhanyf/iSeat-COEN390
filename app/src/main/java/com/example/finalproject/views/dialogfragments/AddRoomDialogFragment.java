/*
    File:           AddRoomDialogFragment.java
    Authors:        Adnan Saab          #40075504
                    Samson Kaller       #40136815
                    Farah Salhany       #40074803
                    Shahin Khalkhali    #40057384
                    Shayan Khalkhali    #40059491
                    Marwan Al-Ghaziri   #40126554
    Description:    This class controls items on the dialog fragment for Admins to add a Room.
*/
package com.example.finalproject.views.dialogfragments;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.finalproject.R;
import com.example.finalproject.models.Room;
import com.google.firebase.database.FirebaseDatabase;

public class AddRoomDialogFragment extends DialogFragment {
    private final String adminEmail;
    private EditText editTextRoomName;
    private EditText editTextRoomLocation;

    // constructor with admin emai argument
    public AddRoomDialogFragment(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    // on create view called when dialog fragment is displayed
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogfragment_add_room, container, false);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        setupUI(view);
        return view;
    }

    // setup user interface elements in dialog fragment
    private void setupUI(View view) {
        // text inputs
        editTextRoomName = view.findViewById(R.id.editTextRoomName);
        editTextRoomLocation = view.findViewById(R.id.editTextRoomLocation);
        // add room button
        ((Button) view.findViewById(R.id.buttonAddRoom)).setOnClickListener(v -> {
            String name = editTextRoomName.getText().toString();
            String location = editTextRoomLocation.getText().toString();
            String errors = checkInputErrors(name, location);
            if (!errors.isEmpty()) {
                Toast.makeText(getContext(), errors, Toast.LENGTH_SHORT).show();
                return;
            }
            new ConfirmDialog(getContext(), new Room(null, name, location, "0/0", adminEmail));
        });
    }

    // validate there is data entered
    private String checkInputErrors(String name, String location) {
        String err;
        err = name.equals("") ? "Please enter a room Name." : "";
        err = location.equals("") ? err + (err.isEmpty() ? "" : "\n") + "Please enter a room Location." : err;
        return err;
    }

    // confirmation dialog for adding room
    private class ConfirmDialog extends AlertDialog.Builder {
        public ConfirmDialog(Context context, Room room) {
            super(context);
            setTitle("Confirm Adding Room:");
            setMessage("\nAdd " + room + "?");
            setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel());
            setPositiveButton(getString(R.string.AddRoom_Button_AddRoom), (dialogInterface, i) -> {
                FirebaseDatabase.getInstance().getReference("rooms").push().setValue(room);
                Toast.makeText(getContext(), "Room added successfully.", Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
                dismiss();
            });
            show();
        }
    }
}
