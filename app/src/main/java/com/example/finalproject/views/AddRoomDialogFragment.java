package com.example.finalproject.views;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public AddRoomDialogFragment(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogfragment_add_room, container);
        setupUI(view);
        return view;
    }

    private void setupUI(View view) {
        editTextRoomName = view.findViewById(R.id.editTextRoomName);
        editTextRoomLocation = view.findViewById(R.id.editTextRoomLocation);
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

    private String checkInputErrors(String name, String location) {
        String err;
        err = name.equals("") ? "Please enter a room Name." : "";
        err = location.equals("") ? err + (err.isEmpty() ? "" : "\n") + "Please enter a room Location." : err;
        return err;
    }

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
