/*
    File:           AdminRoomsRecyclerViewAdaptor.java
    Authors:        Adnan Saab          #40075504
                    Samson Kaller       #40136815
                    Farah Salhany       #40074803
                    Shahin Khalkhali    #40057384
                    Shayan Khalkhali    #40059491
                    Marwan Al-Ghaziri   #40126554
    Description:    This class is the adaptor for the items in the recycler view on the Admin rooms
                    activity.
*/
package com.example.finalproject.views.adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.models.Room;
import com.example.finalproject.views.dialogfragments.AddSensorDialogFragment;
import com.example.finalproject.views.dialogfragments.RemoveSensorDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class AdminRoomsRecyclerViewAdaptor extends RecyclerView.Adapter<AdminRoomsRecyclerViewAdaptor.ViewHolder> {
    private final FragmentManager fragmentManager;
    private final List<Room> localDatSet;
    private final List<CheckBox> checkBoxes;

    public AdminRoomsRecyclerViewAdaptor(List<Room> localDatSet, FragmentManager fragmentManager) {
        this.localDatSet = localDatSet;
        this.fragmentManager = fragmentManager;
        this.checkBoxes = new ArrayList<>();
    }

    @NonNull
    @Override
    public AdminRoomsRecyclerViewAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_admin_rooms_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminRoomsRecyclerViewAdaptor.ViewHolder holder, int position) {
        holder.checkBox.setVisibility(View.GONE);
        checkBoxes.add(holder.checkBox);
        holder.textViewRoom.setText(localDatSet.get(position).toString());
        holder.buttonAddSensor.setOnClickListener(view -> {
            Room room = localDatSet.get(holder.getLayoutPosition());
            new AddSensorDialogFragment(room).show(fragmentManager, "AddSensorDialogFragment");
        });
        holder.buttonRemoveSensor.setOnClickListener(view -> {
            Room room = localDatSet.get(holder.getLayoutPosition());
            new RemoveSensorDialogFragment(room).show(fragmentManager, "RemoveSensorDialogFragment");
        });
    }

    public void showCheckBoxes() {
        for (CheckBox box : checkBoxes)
            box.setVisibility(View.VISIBLE);
    }

    public void hideCheckBoxes() {
        for (CheckBox box : checkBoxes) {
            box.setChecked(false);
            box.setVisibility(View.GONE);
        }
    }

    public List<Room> getCheckedRooms() {
        List<Room> rooms = new ArrayList<>();
        for (int i = 0; i < localDatSet.size(); i++) {
            if (checkBoxes.get(i).isChecked())
                rooms.add(localDatSet.get(i));
        }
        return rooms;
    }

    @Override
    public int getItemCount() {
        return localDatSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox checkBox;
        private final TextView textViewRoom;
        private final ImageButton buttonAddSensor;
        private final ImageButton buttonRemoveSensor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBoxAdminRoomsItem);
            textViewRoom = itemView.findViewById(R.id.textViewRoomAdminRoomsItem);
            buttonAddSensor = itemView.findViewById(R.id.buttonAddSensorAdminRoomsItem);
            buttonRemoveSensor = itemView.findViewById(R.id.buttonRemoveSensorAdminRoomsItem);
        }
    }
}
