package com.example.finalproject.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.models.Room;

import java.util.List;

public class RoomListRecyclerViewAdaptor extends RecyclerView.Adapter<RoomListRecyclerViewAdaptor.ViewHolder> {

    private final List<Room> mRoomList;

    public RoomListRecyclerViewAdaptor(List<Room> mRoomList) {
        this.mRoomList = mRoomList;
    }

    @NonNull
    @Override
    public RoomListRecyclerViewAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_room_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mRoomList.get(position));
    }

    @Override
    public int getItemCount() {
        return mRoomList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mRoomLocation;
        private final TextView mRoomID;
        private final TextView mRoomCapacity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mRoomLocation = itemView.findViewById(R.id.textViewRecycler_topleft);
            mRoomID = itemView.findViewById(R.id.textViewRecyclerView_BottomLeft);
            mRoomCapacity = itemView.findViewById(R.id.textViewRecyclerView_BottomRight);
        }

        public void bind(Room room) {
            mRoomLocation.setText(room.getLocation());
            mRoomID.setText(room.getName());
            mRoomCapacity.setText(room.getCapacity());
        }
    }
}