package com.example.finalproject.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.models.Room;

import java.util.List;

public class AdminRoomsRecyclerViewAdaptor extends RecyclerView.Adapter<AdminRoomsRecyclerViewAdaptor.ViewHolder> {
    private FragmentManager fragmentManager;
    private List<Room> localDatSet;

    public AdminRoomsRecyclerViewAdaptor(List<Room> localDatSet, FragmentManager fragmentManager) {
        this.localDatSet = localDatSet;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public AdminRoomsRecyclerViewAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_admin_rooms_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminRoomsRecyclerViewAdaptor.ViewHolder holder, int position) {
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

    @Override
    public int getItemCount() {
        return localDatSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewRoom;
        private final ImageButton buttonAddSensor;
        private final ImageButton buttonRemoveSensor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRoom = itemView.findViewById(R.id.itemAdminRoomsRoomTextView);
            buttonAddSensor = itemView.findViewById(R.id.itemAdminRoomButtonAddSensor);
            buttonRemoveSensor = itemView.findViewById(R.id.itemAdminRoomButtonRemoveSensor);
        }
    }
}
