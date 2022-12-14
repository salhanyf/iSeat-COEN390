/*
    File:           RegisterActivity.java
    Authors:        Adnan Saab          #40075504
                    Samson Kaller       #40136815
                    Farah Salhany       #40074803
                    Shahin Khalkhali    #40057384
                    Shayan Khalkhali    #40059491
                    Marwan Al-Ghaziri   #40126554
    Description:    This class controls items on the Register activity. Users can enter an email,
                    username, password and admin code to register as a new user.
*/
package com.example.finalproject.views.Registration;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;
import com.example.finalproject.views.Cards.ScanQrCodeCard;
import com.example.finalproject.views.RoomListActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class RegisterActivity extends AppCompatActivity  {
    private final String ADMIN_PASSWORD = "admin";

    private FirebaseAuth auth;
    private EditText signupUsername, signupEmail, signupPassword, signupPasswordRepeat;
    private RadioButton anAdmin;
    private Button signupCreateAccountButton, loginRedirectButton;
    EditText adminCodeSignup;
    ImageButton QrBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        signupUsername = findViewById(R.id.usernameSignup);
        signupEmail = findViewById(R.id.emailSignup);
        signupPassword = findViewById(R.id.passwordSignup);
        signupPasswordRepeat = findViewById(R.id.passwordRepeatSignup);
        anAdmin = findViewById(R.id.adminYes);
        adminCodeSignup = findViewById(R.id.adminCodeSignup);
        signupCreateAccountButton = findViewById(R.id.createAccountButton);
        loginRedirectButton = findViewById(R.id.loginRedirect);
        QrBtn = findViewById(R.id.QrBtn);

        signupCreateAccountButton.setOnClickListener(v -> {
            String username = signupUsername.getText().toString().trim();
            String email = signupEmail.getText().toString().trim();
            String password = signupPassword.getText().toString().trim();
            String passwordRepeat = signupPasswordRepeat.getText().toString().trim();
            String adminPasswordEntered = adminCodeSignup.getText().toString();

            if (!checkErrors(username, email, password, passwordRepeat, adminPasswordEntered)) {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(t -> {
                            if (t.isSuccessful()) {
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").push();
                                databaseReference.child("username").setValue(username);
                                databaseReference.child("email").setValue(email);
                                databaseReference.child("dateCreated").setValue(String.valueOf(new Date()));
                                if (anAdmin.isChecked())
                                    databaseReference.child("isAdmin").setValue(true);
                                else databaseReference.child("isAdmin").setValue(false);
                                Toast.makeText(this, "User Registration & Login Success", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "User Registration Success but Login Failed", Toast.LENGTH_SHORT).show();
                            }
                            startActivity(new Intent(this, RoomListActivity.class));
                            finishAffinity();
                        });
                    }
                });
            } else {
                Toast.makeText(this, "User Registration Failed", Toast.LENGTH_SHORT).show();
            }
        });

        loginRedirectButton.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private boolean checkErrors(String username, String email, String password, String passwordRepeat, String adminPasswordEntered) {
        boolean err = false;
        if (username.isEmpty()) {
            signupUsername.setError("Please enter your username");
            err = true;
        }
        if (email.isEmpty()) {
            signupEmail.setError("Please enter your email");
            err = true;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signupEmail.setError("Please Enter Valid Email");
            err = true;
        }
        if (password.isEmpty()) {
            signupPassword.setError("Please enter your password");
            err = true;
        }
        if (passwordRepeat.isEmpty() || !passwordRepeat.equals(password)) {
            signupPasswordRepeat.setError("Password does not match!");
            err = true;
        }
        if (anAdmin.isChecked() && !adminPasswordEntered.equals(ADMIN_PASSWORD)) {
            adminCodeSignup.setError("Admin code is incorrect");
            err = true;
        }
        return err;
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        //check which radio button was clicked
        switch (view.getId()) {
            case R.id.adminYes:
                adminCodeSignup.setVisibility(View.VISIBLE);
                QrBtn.setVisibility(View.VISIBLE);
                QrBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivityForResult(new Intent(RegisterActivity.this, ScanQrCodeCard.class), 0);
                    }
                });
                break;
            case R.id.adminNo:
                adminCodeSignup.setVisibility(View.GONE);
                QrBtn.setVisibility(View.GONE);
                break;
        }
    }

    public void setTextAdminCode(){



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String QrCode = data.getStringExtra("adminCode");

    if (!QrCode.isEmpty()) {
        adminCodeSignup.setText(QrCode);

    }
//        if(requestCode==0)
//        {
//            Toast.makeText(this, "Qr Code Failed", Toast.LENGTH_SHORT).show();
//        }
    }

}