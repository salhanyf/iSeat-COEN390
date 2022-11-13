package com.example.finalproject.views;

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

import com.example.finalproject.R;
import com.example.finalproject.controllers.FirebaseDatabaseHelper;
import com.example.finalproject.models.Room;
import com.example.finalproject.models.Sensor;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AddSensorDialogFragment extends DialogFragment {
    private final Room room;        // info on room (name, location)
    private FirebaseDatabaseHelper db;
    private TextView textViewNoSensors;         // shows No sensor message if none available
    private ListView listViewAvailableSensors;  // lists available sensors (not assigned to room)

    // constructor calls DialogFragment parent constructor, sets roomKey for later
    public AddSensorDialogFragment(Room room) {
        super();
        this.room = room;
    }

    // onCreateView() called when dialog fragment opens
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogfragment_add_sensor, container);
        setupUI(view);
        db = new FirebaseDatabaseHelper();
        db.listenToSensorsRoom("", new UpdateSensorsListView());
        return view;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        db.removeSensorsListeners();
    }

    // setup UI elements
    private void setupUI(View view) {
        ((TextView) view.findViewById(R.id.textViewSelectedRoom)).setText(String.format("%s\n%s", getString(R.string.AddSensor_TextView_SelectedRoom), room));
        textViewNoSensors = view.findViewById(R.id.textViewNoneAvailable);
        listViewAvailableSensors = view.findViewById(R.id.listViewAvailableSensors);
    }

    private class UpdateSensorsListView implements FirebaseDatabaseHelper.SensorDataChange {
        @Override
        public void dataUpdated(List<Sensor> sensors) {
            if (sensors.isEmpty()) {
                textViewNoSensors.setVisibility(View.VISIBLE);
                listViewAvailableSensors.setVisibility(View.GONE);
            } else {
                listViewAvailableSensors.setVisibility(View.VISIBLE);
                textViewNoSensors.setVisibility(View.GONE);
                // add ArrayAdaptor with method overrides for Sensor item in listview
                listViewAvailableSensors.setAdapter(new AvailableSensorsArrayAdaptor(getContext(), sensors));
            }
        }
    }

    private class AvailableSensorsArrayAdaptor extends ArrayAdapter<Sensor> {
        private List<Sensor> sensors;

        public AvailableSensorsArrayAdaptor(Context context, List<Sensor> sensors) {
            super(context, android.R.layout.simple_list_item_1, sensors);
            this.sensors = sensors;
        }

        // getView() controls how data looks, rather than create a new .xml layout just for a textview
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            // customize textview
            TextView textViewSensor = new TextView(getContext());
            textViewSensor.setGravity(Gravity.CENTER);
            textViewSensor.setMinHeight(50);
            textViewSensor.setPadding(10, 10, 10, 10);
            textViewSensor.setTextColor(getContext().getColor(R.color.black));
            // set the items onClickListener to open a Confirmation Dialog for adding sensor
            textViewSensor.setOnClickListener(view -> (new ConfirmDialog(getContext(), sensors.get(position).getKey())).show());
            return super.getView(position, textViewSensor, parent);
        }
    }

    // confirmation dialog for adding a sensor
    private class ConfirmDialog extends AlertDialog.Builder {
        // do everything in constructor
        public ConfirmDialog(Context context, String sensorKey) {
            super(context);
            setTitle(room.toString());
            setMessage("Confirm adding sensor " + sensorKey + " to room.");
            setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel());
            setPositiveButton("Confirm", ((dialogInterface, i) -> {
                // update the sensors roomKey to this room
                FirebaseDatabase.getInstance().getReference("sensors/" + sensorKey + "/roomKey").setValue(room.getKey());
                // print success toast and dismiss dialog fragment
                Toast.makeText(getContext(), "Added " + sensorKey + " to " + room, Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
                dismiss();
            }));
        }
    }
}
