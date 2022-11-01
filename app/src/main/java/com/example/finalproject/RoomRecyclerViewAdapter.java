package com.example.finalproject;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.database.entity.RoomEntity;

import java.util.List;

public class RoomRecyclerViewAdapter extends RecyclerView.Adapter<RoomRecyclerViewAdapter.ViewHolder> {
    private List<RoomEntity> localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView roomLocationTextView;
        public TextView roomIDTextView;
        public TextView roomSeatsTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            roomLocationTextView = itemView.findViewById(R.id.roomLocation_textview2);
            roomIDTextView = itemView.findViewById(R.id.roomID_textview2);
            roomSeatsTextView = itemView.findViewById(R.id.room_seats_textview2);
        }

        public TextView getRoomLocationTextView() {
            return roomLocationTextView;
        }
        public TextView getRoomNameTextView() {
            return roomIDTextView;
        }
        public TextView getRoomSeatsTextView() { return roomSeatsTextView; }
    }

    public RoomRecyclerViewAdapter(List<RoomEntity> localDataSet) {
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
        holder.getRoomSeatsTextView().setText(localDataSet.get(position).studyRoomSeats);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getLayoutPosition();

                Intent intent = new Intent(v.getContext(), RoomInfoActivity.class);
                intent.putExtra("room_uid", localDataSet.get(position).studyRoomID);
                v.getContext().startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }


}
