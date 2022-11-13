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
import com.example.finalproject.controllers.FirebaseDatabaseHelper;
import com.example.finalproject.models.Room;
import com.example.finalproject.models.Sensor;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RemoveSensorDialogFragment extends DialogFragment {
    private final Room room;
    FirebaseDatabaseHelper db;
    private TextView textViewNoneAssigned;
    private ListView listViewSensorsInRoom;

    public RemoveSensorDialogFragment(Room room) {
        super();
        this.room = room;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogfragment_remove_sensor, container);
        setupUI(view);
        db = new FirebaseDatabaseHelper();
        db.listenToSensorsRoom(room.getKey(), new UpdateSensorsListView());
        return view;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        db.removeSensorsListeners();
    }

    private void setupUI(View view) {
        ((TextView) view.findViewById(R.id.textViewForRoom)).setText(String.format("%s\n%s", getString(R.string.RemoveSensor_TextView_ForRoom), room));
        textViewNoneAssigned = view.findViewById(R.id.textViewNoneAssigned);
        listViewSensorsInRoom = view.findViewById(R.id.listViewSensorsInRoom);
    }

    private class UpdateSensorsListView implements FirebaseDatabaseHelper.SensorDataChange {
        @Override
        public void dataUpdated(List<Sensor> sensors) {
            if (sensors.isEmpty()) {
                textViewNoneAssigned.setVisibility(View.VISIBLE);
                listViewSensorsInRoom.setVisibility(View.GONE);
            } else {
                listViewSensorsInRoom.setVisibility(View.VISIBLE);
                textViewNoneAssigned.setVisibility(View.GONE);
                // add ArrayAdaptor with method overrides for Sensor item in listview
                listViewSensorsInRoom.setAdapter(new SensorsInRoomArrayAdaptor(getContext(), sensors));
            }
        }
    }

    private class SensorsInRoomArrayAdaptor extends ArrayAdapter<Sensor> {
        private List<Sensor> sensors;

        public SensorsInRoomArrayAdaptor(Context context, List<Sensor> sensors) {
            super(context, android.R.layout.simple_list_item_1, sensors);
            this.sensors = sensors;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            TextView textView = new TextView(getContext());
            textView.setGravity(Gravity.CENTER);
            textView.setMinHeight(50);
            textView.setPadding(10, 10, 10, 10);
            textView.setTextColor(getContext().getColor(R.color.black));
            textView.setOnClickListener(view -> (new ConfirmDialog(getContext(), sensors.get(position).getKey())).show());
            return super.getView(position, textView, parent);
        }
    }

    private class ConfirmDialog extends AlertDialog.Builder {
        public ConfirmDialog(Context context, String sensorKey) {
            super(context);
            setTitle(room.toString());
            setMessage("Confirm removing sensor " + sensorKey + " from room.");
            setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel());
            setPositiveButton("Confirm", ((dialogInterface, i) -> {
                // get reference to Firebase for the sensor's roomKey
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("sensors/" + sensorKey + "/roomKey");
                ref.setValue("");
                // print success toast and dismiss dialog fragment
                Toast.makeText(getContext(), "Remove " + sensorKey + " from " + room.toString(), Toast.LENGTH_SHORT).show();
                dismiss();
            }));
        }
    }
}
