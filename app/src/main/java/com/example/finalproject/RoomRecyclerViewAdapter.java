package com.example.finalproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.database.entity.Sensor;

import java.util.List;

public class RoomRecyclerViewAdapter extends RecyclerView.Adapter<RoomRecyclerViewAdapter.ViewHolder> {
    private List<Sensor> localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView roomLocationTextView;
        public TextView roomIDTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            roomLocationTextView = itemView.findViewById(R.id.roomLocation_textview2);
            roomIDTextView = itemView.findViewById(R.id.roomID_textview2);
        }

        public TextView getRoomLocationTextView() {
            return roomLocationTextView;
        }
        public TextView getRoomNameTextView() {
            return roomIDTextView;
        }
    }

    public RoomRecyclerViewAdapter(List<Sensor> localDataSet) {
        this.localDataSet = localDataSet;
    }

    @NonNull
    @Override
    public RoomRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.getRoomLocationTextView().setText(localDataSet.get(position).studyRoomLocation);
        holder.getRoomNameTextView().setText(localDataSet.get(position).studyRoomName);
    }
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }


}
