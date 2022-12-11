/*
    File:           UserListAdaptor.java
    Authors:        Adnan Saab          #40075504
                    Samson Kaller       #40136815
                    Farah Salhany       #40074803
                    Shahin Khalkhali    #40057384
                    Shayan Khalkhali    #40059491
                    Marwan Al-Ghaziri   #40126554
    Description:    This class is the adaptor for the items in the recycler view on the User List
                    card that admins can consult and see the registered users.
*/
package com.example.finalproject.views.adaptors;

import android.util.Log;
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
        TextView txtName, txtEmail, txtRole, txtDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.textViewRecycler_topleft_users);
            txtEmail = itemView.findViewById(R.id.textViewRecyclerView_BottomLeft_users);
            txtRole = itemView.findViewById(R.id.textViewRecyclerView_topRight_users);
            txtDate = itemView.findViewById(R.id.textViewRecyclerView_BottomRight_users);
        }

        public void bind(User user) {
            if (user.getIsAdmin()) {
                txtRole.setText("Role: Admin");
            } else {
                txtRole.setText("Role: User");
            }
            txtName.setText(user.getUsername());
            txtEmail.setText(user.getEmail());
            txtDate.setText("Date Created: " + user.getDateCreated());
            Log.w("TAG", "bind: " + user.getDateCreated());
        }
    }
}

