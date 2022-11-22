package com.example.finalproject.views.adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.controllers.FirebaseDatabaseHelper;
import com.example.finalproject.models.Room;
import com.example.finalproject.models.Sensor;
import com.example.finalproject.views.RoomClickedActivity;

import java.util.List;

public class RoomListRecyclerViewAdaptor extends RecyclerView.Adapter<RoomListRecyclerViewAdaptor.ViewHolder> {

    private final List<Room> mRooms;
    private final Context context;

    public RoomListRecyclerViewAdaptor(Context context, List<Room> mRooms) {
        this.context = context;
        this.mRooms = mRooms;
    }

    @NonNull
    @Override
    public RoomListRecyclerViewAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_room_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mRooms.get(position));
        new FirebaseDatabaseHelper().listenToSensorsRoom(mRooms.get(position).getKey(), new UpdateCapacityTextView(holder.mRoomCapacity, holder));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add the room key to the intent and start the activity
                Intent intent = new Intent(context, RoomClickedActivity.class);
                intent.putExtra("roomKey", mRooms.get(holder.getAdapterPosition()).getKey());
                intent.putExtra("roomName", mRooms.get(holder.getAdapterPosition()).getName());
                intent.putExtra("roomCapacity", mRooms.get(holder.getAdapterPosition()).getCapacity());
                intent.putExtra("roomLocation", mRooms.get(holder.getAdapterPosition()).getLocation());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRooms.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mRoomName;
        private final TextView mRoomLocation;
        private final TextView mRoomCapacity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mRoomName = itemView.findViewById(R.id.textViewRecyclerView_BottomLeft);
            mRoomLocation = itemView.findViewById(R.id.textViewRecycler_topleft);
            mRoomCapacity = itemView.findViewById(R.id.textViewRecyclerView_BottomRight);
        }

        public void bind(Room room) {
            mRoomName.setText(room.getName());
            mRoomLocation.setText(room.getLocation());
            mRoomCapacity.setText(room.getCapacity());
        }
    }

    private class UpdateCapacityTextView implements FirebaseDatabaseHelper.SensorDataChange {
        private final TextView textView;
        private final ViewHolder viewHolder;

        public UpdateCapacityTextView(TextView textView, ViewHolder holder) {
            this.textView = textView;
            this.viewHolder = holder;
        }

        @Override
        public void dataUpdated(List<Sensor> sensors) {
            int open = 0, total = 0;
            for (Sensor sensor : sensors) {
                if (sensor.getStatus())
                    open++;
                total++;
            }
            textView.setText(String.format(context.getString(R.string.RoomList_TextView_Capacity), open, total));
            //makes seats update in class Room
            mRooms.get(viewHolder.getAdapterPosition()).setCapacity(String.format(context.getString(R.string.RoomList_TextView_Capacity), open, total));
        }
    }
}