package com.example.finalproject.views.adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.models.User;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {
    private List<User> usersList = new ArrayList<>();

    public UserListAdapter(DataSnapshot dataSnapshot) {
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            User user = snapshot.getValue(User.class);
            usersList.add(user);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_user_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(usersList.get(position));
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtEmail, txtRole;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.textViewRecycler_topleft_users);
            txtEmail = itemView.findViewById(R.id.textViewRecyclerView_BottomLeft_users);
            txtRole = itemView.findViewById(R.id.textViewRecyclerView_topRight_users);
        }

        public void bind(User user) {
            if (user.getIsAdmin()) {
                txtRole.setText("Role: Admin");
            } else {
                txtRole.setText("Role: User");
            }
            txtName.setText("Username: " + user.getUsername());
            txtEmail.setText("Email: " + user.getEmail());
        }
    }
}

