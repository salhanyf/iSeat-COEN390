package com.example.finalproject.views.adaptors;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.controllers.FirebaseDatabaseHelper;
import com.example.finalproject.models.Room;
import com.example.finalproject.models.Sensor;
import com.example.finalproject.views.Registration.LoginActivity;
import com.example.finalproject.views.RoomClickedActivity;
import com.example.finalproject.views.RoomListActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RoomListRecyclerViewAdaptor extends RecyclerView.Adapter<RoomListRecyclerViewAdaptor.ViewHolder> {

    private final List<Room> mRooms;
    private final Context context;
//    private static boolean isBookmarked;
    private String email;

    public RoomListRecyclerViewAdaptor(Context context, List<Room> mRooms, String email) {
        this.context = context;
        this.mRooms = mRooms;
        this.email = email;
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
        String roomKey = mRooms.get(position).getKey();
        new FirebaseDatabaseHelper().listenToSensorsRoom(roomKey, new UpdateCapacityTextView(holder.mRoomCapacity));

        holder.itemView.setOnClickListener(v -> {
            // add the room key to the intent and start the activity
            Intent intent = new Intent(context, RoomClickedActivity.class);
            intent.putExtra("roomKey", mRooms.get(holder.getAdapterPosition()).getKey());
            intent.putExtra("roomName", mRooms.get(holder.getAdapterPosition()).getName());
            intent.putExtra("roomCapacity", mRooms.get(holder.getAdapterPosition()).getCapacity());
            intent.putExtra("roomLocation", mRooms.get(holder.getAdapterPosition()).getLocation());
            context.startActivity(intent);
        });

        holder.email = email;
    }

    @Override
    public int getItemCount() {
        return mRooms.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mRoomName;
        private final TextView mRoomLocation;
        private final TextView mRoomCapacity;
        private boolean isBookmarked;
        private ImageView favoriteButton;
        private String email;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mRoomName = itemView.findViewById(R.id.textViewRecycler_topleft_users);
            mRoomLocation = itemView.findViewById(R.id.textViewRecyclerView_BottomLeft_users);
            mRoomCapacity = itemView.findViewById(R.id.textViewRecyclerView_BottomRight_users);
            favoriteButton = itemView.findViewById(R.id.addToFavoriteButton);
            isBookmarked = false;
            this.email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        }

        public void bind(Room room) {
            mRoomName.setText(room.getName());
            mRoomLocation.setText(room.getLocation());
            mRoomCapacity.setText(room.getCapacity());
            String roomKey = room.getKey();

            DatabaseReference users = FirebaseDatabase.getInstance().getReference("users");
            users.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        Log.d("TESTING", "user key: " + snap.getKey());
                        users.child(snap.getKey()).child("bookmarks").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot snap : snapshot.getChildren()) {
                                    if ((snap.getKey().equals(roomKey)) && ((Boolean) snap.getValue())) {
                                        Log.d("TESTING", "Fav: " + snap.getKey());
                                        favoriteButton.setImageResource(R.drawable.account_activity_bookmark);
                                        isBookmarked = true;
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

            favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isBookmarked){
                        favoriteButton.setImageResource(R.drawable.account_activity_bookmark_border);
                        FirebaseDatabase.getInstance().getReference("users").orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot snap : snapshot.getChildren()) {
                                    FirebaseDatabase.getInstance().getReference("users").child(snap.getKey()).child("bookmarks").child(room.getKey()).removeValue();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        Toast.makeText(view.getContext(), "Room is removed from favourite", Toast.LENGTH_SHORT).show();
                        isBookmarked = false;
                    }
                    else{
                        favoriteButton.setImageResource(R.drawable.account_activity_bookmark);
                        FirebaseDatabase.getInstance().getReference("users").orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot snap : snapshot.getChildren()) {
                                    FirebaseDatabase.getInstance().getReference("users").child(snap.getKey()).child("bookmarks").child(room.getKey()).setValue(true);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        Toast.makeText(view.getContext(), "Room saved", Toast.LENGTH_SHORT).show();
                        isBookmarked = true;
                    }
                }
            });
        }
    }

    public class UpdateCapacityTextView implements FirebaseDatabaseHelper.SensorDataChange {
        private final TextView textView;

        public UpdateCapacityTextView(TextView textView) {
            this.textView = textView;
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
        }
    }
}