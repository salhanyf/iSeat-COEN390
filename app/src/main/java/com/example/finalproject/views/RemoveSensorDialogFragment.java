package com.example.finalproject.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
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

import com.example.finalproject.R;
import com.example.finalproject.models.Room;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RemoveSensorDialogFragment extends DialogFragment {
    private final String roomKey;
    private Room room;
    private List<String> list;
    private TextView textViewForRoom;
    private TextView textViewNoneAssigned;
    private ListView listViewSensorsInRoom;

    private Query q;
    private ValueEventListener listener;

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
        ref.child("rooms").child(roomKey).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                room = task.getResult().getValue(Room.class);
                textViewForRoom.setText(String.format("%s \n%s", getString(R.string.RemoveSensor_TextView_ForRoom), room.toString()));
            } else {
                Toast.makeText(getContext(), "Error reading rooms for roomKey=", Toast.LENGTH_LONG).show();
            }
        });

        q = ref.child("sensors").orderByChild("roomID").equalTo(Integer.parseInt(roomKey));
        listener = q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();

                for (DataSnapshot snap : snapshot.getChildren()) {
                    list.add(snap.getKey());
                }

                if (list.isEmpty()) {
                    textViewNoneAssigned.setVisibility(View.VISIBLE);
                    listViewSensorsInRoom.setVisibility(View.GONE);
                } else {
                    listViewSensorsInRoom.setVisibility(View.VISIBLE);
                    textViewNoneAssigned.setVisibility(View.GONE);

                    listViewSensorsInRoom.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list) {
                        @NonNull
                        @Override
                        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                            TextView textView = new TextView(getContext());
                            textView.setGravity(Gravity.CENTER);
                            textView.setPadding(10, 10, 10, 10);
                            textView.setTextColor(getContext().getColor(R.color.black));
                            textView.setOnClickListener(view -> (new ConfirmDialog(getContext(), list.get(position))).show());
                            return super.getView(position, textView, parent);
                        }

                        class ConfirmDialog extends AlertDialog.Builder {
                            public ConfirmDialog(Context context, String sensorKey) {
                                super(context);
                                setTitle(room.toString());
                                setMessage("Confirm removing sensor " + sensorKey + " from room.");
                                setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel());
                                setPositiveButton("Confirm", ((dialogInterface, i) -> {
                                    // get reference to Firebase for the sensor's roomID
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("sensors/" + sensorKey + "/roomID");
                                    // update the sensors roomID to this room
                                    ref.setValue(0);
                                    // print success toast and dismiss dialog fragment
                                    Toast.makeText(getContext(), "Remove " + sensorKey + " from " + room.toString(), Toast.LENGTH_SHORT).show();
                                    dismiss();
                                }));
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error reading Sensors: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        q.removeEventListener(listener);
        super.onDismiss(dialog);
    }
}
