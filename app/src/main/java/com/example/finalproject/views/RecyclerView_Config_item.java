package com.example.finalproject.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.models.Room;

import java.util.List;

public class RecyclerView_Config_item {

    private Context mContext;
    private RoomsAdapter mRoomsAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<Room> rooms, List<String> keys) {
        mContext = context;
        mRoomsAdapter = new RoomsAdapter(rooms, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mRoomsAdapter);
    }

    class RoomItemView extends RecyclerView.ViewHolder {
        private TextView mRoomLocation;
        private TextView mRoomID;
        private TextView mRoomCapacity;

        private String key;

        public RoomItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item_room, parent, false));

            mRoomLocation = (TextView) itemView.findViewById(R.id.textViewRecycler_topleft);
            mRoomID = (TextView) itemView.findViewById(R.id.textViewRecyclerView_BottomLeft);
            mRoomCapacity = (TextView) itemView.findViewById(R.id.textViewRecyclerView_BottomRight);
        }
        public void bind(Room room, String key) {
            mRoomLocation.setText(room.getLocation());
            mRoomID.setText(room.getName());
            mRoomCapacity.setText(room.getCapacity());
            this.key = key;
        }
    }

    class RoomsAdapter extends RecyclerView.Adapter<RoomItemView>{
        private List<Room> mRoomList;
        private List<String> mKeys;

        public RoomsAdapter(List<Room> mRoomList, List<String> mKeys) {
            this.mRoomList = mRoomList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public RoomItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RoomItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RoomItemView holder, int position) {
            holder.bind(mRoomList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mRoomList.size();
        }
    }
}
