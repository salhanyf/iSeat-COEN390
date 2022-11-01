package com.example.finalproject;

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
import androidx.appcompat.app.AlertDialog;
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
    private final String roomKey;   // room key passed to dialog fragment
    private Room room;              // info on room (name, location)
    private List<String> sensors;   // global list of sensor id Keys
    private ListView listViewAvailableSensors;  // lists available sensors (not assigned to room)
    private TextView textViewNoSensors;         // shows No sensor message if none available
    private TextView textViewSelectedRoom;      // shows the room sensor is being added to
    private Query queryAvailableSensors;            // active query for changes in Firebase sensor data
    private ValueEventListener listenerSensorData;  // active listener for changes in Firebase sensor data

    // constructor calls DialogFragment parent constructor, sets roomKey for later
    public AddSensorDialogFragment(String roomKey) {
        super();
        this.roomKey = roomKey;
    }

    // onCreateView() called when dialog fragment opens
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogfragment_add_sensor, container);
        setupUI(view);
        setupDB();
        return view;
    }

    // setup UI elements
    private void setupUI(View view) {
        textViewNoSensors = view.findViewById(R.id.textViewNoneAvailable);
        textViewSelectedRoom = view.findViewById(R.id.textViewSelectedRoom);
        listViewAvailableSensors = view.findViewById(R.id.listViewAvailableSensors);
    }

    // set up queries for data from Firebase
    private void setupDB() {
        // get reference to Firebase table (root node)
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        // get room data (name, location) using roomKey (asynchronous, only once, custom OnCompleteListener see class below)
        db.child("rooms").child(roomKey).get().addOnCompleteListener(new GetRoomOnCompleteListener());
        // make a query for sensors whose roomID are 0
        queryAvailableSensors = db.child("sensors").orderByChild("roomID").equalTo(0);
        // add listener for data changes in sensor roomIDs on Firebase (asynchronous, continual, custom ValueEventListener see class below)
        listenerSensorData = queryAvailableSensors.addValueEventListener(new GetSensorsValueEventListener());
    }

    // onDismiss() called when dialog fragment closes
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        // remove the event listener (many bugs until I found out this line)
        queryAvailableSensors.removeEventListener(listenerSensorData);
        super.onDismiss(dialog);
    }

    // custom ValueEventListener for changes in Available sensors
    private class GetSensorsValueEventListener implements ValueEventListener {
        // onDataChange() when roomID for a sensor modified in Firebase
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            sensors = new ArrayList<>(); // create new list

            // read keys of available sensors from Firebase DataSnapshot
            // snapshot contains all sensors (json-list-like thing)
            // for loop cycles through its children
            for (DataSnapshot sensor : snapshot.getChildren())
                sensors.add(sensor.getKey()); // add sensor key to list

            // when list is empty, set No Sensor message textview to visible and listview of sensors to gone
            // else we have something in list, set NoSensor message to gone and set adaptor for listView available sensors
            if (sensors.isEmpty()) {
                textViewNoSensors.setVisibility(View.VISIBLE);
                listViewAvailableSensors.setVisibility(View.GONE);
            } else {
                listViewAvailableSensors.setVisibility(View.VISIBLE);
                textViewNoSensors.setVisibility(View.GONE);

                // add ArrayAdaptor with method overrides for Sensor item in listview
                listViewAvailableSensors.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, sensors) {
                    // getView() controls how data looks, rather than create a new .xml layout just for a textview
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        TextView textViewSensor = makeTextView();
                        // set the items onClickListener to open a Confirmation Dialog for adding sensor
                        textViewSensor.setOnClickListener(view -> (new ConfirmDialog(getContext(), sensors.get(position))).show());
                        return super.getView(position, textViewSensor, parent);
                    }

                    // customize textview
                    private TextView makeTextView() {
                        TextView textView = new TextView(getContext());
                        textView.setGravity(Gravity.CENTER);
                        textView.setPadding(10, 10, 10, 10);
                        textView.setTextColor(getContext().getColor(R.color.black));
                        return textView;
                    }

                    // confirmation dialog for adding a sensor
                    class ConfirmDialog extends AlertDialog.Builder {
                        // do everything in constructor
                        public ConfirmDialog(Context context, String sensorKey) {
                            super(context);
                            setTitle(room.toString());
                            setMessage("Confirm adding sensor " + sensorKey + " to room.");
                            setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel());
                            setPositiveButton("Confirm", ((dialogInterface, i) -> {
                                // get reference to Firebase for the sensor's roomID
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("sensors/" + sensorKey + "/roomID");
                                // update the sensors roomID to this room
                                ref.setValue(Integer.parseInt(roomKey));
                                // print success toast and dismiss dialog fragment
                                Toast.makeText(getContext(), "Added " + sensorKey + " to " + room.toString(), Toast.LENGTH_SHORT).show();
                                dismiss();
                            }));
                        }
                    }
                });
            }
        }

        // Error reading data from firebase using the ValueEventListener
        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(getContext(), "Error reading Sensors: " + error.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // custom 1-time only OnCompleteListener for reading room info
    private class GetRoomOnCompleteListener implements OnCompleteListener<DataSnapshot> {
        @Override
        public void onComplete(@NonNull Task<DataSnapshot> task) {
            if (task.isSuccessful()) {
                // convert room Info from Firebase into class
                room = task.getResult().getValue(Room.class);
                // set textview for selected room
                textViewSelectedRoom.setText(String.format("%s \n%s", getString(R.string.AddSensor_TextView_SelectedRoom), room.toString()));
            } else {
                // print error if occurs
                Toast.makeText(getContext(), "Error reading rooms for roomKey=" + roomKey, Toast.LENGTH_LONG).show();
            }
        }
    }
}
