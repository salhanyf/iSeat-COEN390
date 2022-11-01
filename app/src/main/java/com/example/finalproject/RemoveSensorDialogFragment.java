package com.example.finalproject;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.finalproject.models.Room;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RemoveSensorDialogFragment extends DialogFragment {
    private final String roomKey;
    private Room room;
    private TextView textViewForRoom;
    private TextView textViewNoneAssigned;
    private ListView listViewSensorsInRoom;


    public RemoveSensorDialogFragment(String roomKey) {
        super();
        this.roomKey = roomKey;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogfragment_remove_sensor, container);

        textViewForRoom = view.findViewById(R.id.textViewForRoom);
        textViewNoneAssigned = view.findViewById(R.id.textViewNoneAssigned);
        listViewSensorsInRoom = view.findViewById(R.id.listViewSensorsInRoom);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("sensors").child(roomKey).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                room = task.getResult().getValue(Room.class);
                textViewForRoom.setText(String.format("%s \n%s", getString(R.string.RemoveSensor_TextView_ForRoom), room.toString()));
            } else {
                Toast.makeText(getContext(), "Error reading rooms for roomKey=", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
    }
}
