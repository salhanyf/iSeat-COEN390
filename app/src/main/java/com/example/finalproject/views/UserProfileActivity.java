package com.example.finalproject.views;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;
import com.google.firebase.auth.FirebaseAuth;


public class UserProfileActivity extends AppCompatActivity {
    // Create a new user profile activity
    private String username;
    private String email;
    private String password;
    private String phoneNumber;

    private TextView usernameTextView;
    private TextView emailTextView;
    private TextView passwordTextView;
    private TextView phoneNumberTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Get the username, email, password, and phone number from the user from firebase and display it on the screen
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        username = firebaseAuth.getCurrentUser().getDisplayName();
        email = firebaseAuth.getCurrentUser().getEmail();
//        password = firebaseAuth.getCurrentUser().getPassword();
        phoneNumber = firebaseAuth.getCurrentUser().getPhoneNumber();

        //set the text of the text views to the user's information
        usernameTextView = findViewById(R.id.UserNameTextView);
        usernameTextView.setText("Username: " + username);
        emailTextView = findViewById(R.id.EmailTextView);
        emailTextView.setText("Email: " + email);
//        passwordTextView = findViewById(R.id.passwordTextView);
//        passwordTextView.setText(password);
        phoneNumberTextView = findViewById(R.id.PhoneNumberTextView);
        phoneNumberTextView.setText("Phone Number: " + phoneNumber);


        // Display the user's information in the user profile activity
    }

    // Create a method to update the user's information
    public void updateUserInfo() {
        // Update the user's information
    }

    // Create a method to delete the user's account
    public void deleteUser() {
        // Delete the user's account
    }

    // Create a method to log the user out
    public void logout() {
        // Log the user out
    }

    // Create a method to display the user's information
    public void displayUserInfo() {
        // Display the user's information
    }

    // Create a method to display the user's saved rooms
    public void displaySavedRooms() {
        // Display the user's saved rooms
    }


}