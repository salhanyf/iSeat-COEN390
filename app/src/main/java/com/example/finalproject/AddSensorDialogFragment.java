package com.example.finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddSensorDialogFragment extends DialogFragment {
    private final String roomKey;

    private TextView textViewSelectedRoom;
    private ListView listViewAvailableSensors;
    private List<String> list;

    public AddSensorDialogFragment(String roomKey) {
        super();
        this.roomKey = roomKey;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogfragment_add_sensor, container);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        textViewSelectedRoom = view.findViewById(R.id.textViewSelectedRoom);
        ref.child("rooms").child(roomKey).get().addOnCompleteListener(new GetRoomOnCompleteListener());

        listViewAvailableSensors = view.findViewById(R.id.listViewAvailableSensors);

        Query query = ref.child("sensors").orderByChild("roomID").equalTo(0);
        query.addValueEventListener(new GetSensorsValueEventListener());

        return view;
    }

    private class GetSensorsValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            list = new ArrayList<>();
            for (DataSnapshot snap : snapshot.getChildren()) {
                list.add(snap.getKey());
            }
            listViewAvailableSensors.setAdapter(new ArrayAdapter<>(getContext(), R.layout.listview_sensor, list.toArray(new String[0])));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(getContext(), "Error reading Sensors", Toast.LENGTH_LONG).show();
        }
    }

    private class GetRoomOnCompleteListener implements OnCompleteListener<DataSnapshot> {
        @Override
        public void onComplete(@NonNull Task<DataSnapshot> task) {
            if (task.isSuccessful())
                textViewSelectedRoom.setText(String.format("%s \n%s", getString(R.string.AddSensor_TextView_SelectedRoom), task.getResult().getValue(Room.class).toString()));
            else
                Toast.makeText(getContext(), "Error reading Room for rooms key=" + roomKey, Toast.LENGTH_LONG).show();
        }
    }
}
