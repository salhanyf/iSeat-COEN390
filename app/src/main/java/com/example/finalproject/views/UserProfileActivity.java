package com.example.finalproject.views;

import android.app.ActionBar;

import androidx.appcompat.widget.Toolbar;


public class UserProfileActivity {
    // Create a new user profile activity
    private Toolbar toolbar;
    private ActionBar actionBar;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;


    public UserProfileActivity(String username, String email, String password, String phoneNumber) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    // Set the user profile data
    public void setUserProfileActivity() {
        actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("User Profile");
    }

    private ActionBar getActionBar() {
        return null;
    }


}