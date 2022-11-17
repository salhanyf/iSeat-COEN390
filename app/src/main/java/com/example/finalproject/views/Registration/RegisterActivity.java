package com.example.finalproject.views.Registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.views.RoomListActivity;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private final String ADMIN_PASSWORD = "admin";

    private FirebaseAuth auth;
    private EditText signupUsername, signupEmail, signupPassword, signupPasswordRepeat, adminCodeSignup;
    private RadioButton anAdmin, notAnAdmin;
    private String accountType = "user";
    private String adminPasswordEntered;
    private Button signupCreateAccountButton, loginRedirectButton;

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
        //notAnAdmin = findViewById(R.id.adminNo);
        adminCodeSignup = findViewById(R.id.adminCodeSignup);
        signupCreateAccountButton = findViewById(R.id.createAccountButton);
        loginRedirectButton = findViewById(R.id.loginRedirect);

        loginRedirectButton.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        signupCreateAccountButton.setOnClickListener(v -> {
            String username = signupUsername.getText().toString().trim();
            String email = signupEmail.getText().toString().trim();
            String password = signupPassword.getText().toString().trim();
            String passwordRepeat = signupPasswordRepeat.getText().toString().trim();
            String adminPasswordEntered = adminCodeSignup.getText().toString();

            if (!checkErrors(username, email, password, passwordRepeat, adminPasswordEntered)) {

                if (anAdmin.isChecked())
                    FirebaseDatabase.getInstance().getReference("admins").push().setValue(email);

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                    task -> {
                        if (task.isSuccessful()) {
                            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(t -> {
                                if (t.isSuccessful())
                                    Toast.makeText(this, "User Registration & Login Success", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(this, "User Registration Success but Login Failed", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(this, RoomListActivity.class));
                                finishAffinity();
                            });
                        } else {
                            adminCodeSignup.setError("Admin code is incorrect");
                        }
                    //if user is not an admin
                    } else {
                        //Toast.makeText(RegisterActivity.this, "Not and admin", Toast.LENGTH_SHORT).show();
                        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //TODO: redirect to main page instead of logging in again?
                                    Toast.makeText(RegisterActivity.this, "User Successfully Registered", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                } else {
                                    Toast.makeText(RegisterActivity.this, "User Registration Failed, Try Again", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });

        loginRedirectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        //check which radio button was clicked
        switch(view.getId()) {
            case R.id.adminYes:
                adminCodeSignup.setVisibility(View.VISIBLE);
                break;
            case R.id.adminNo:
                adminCodeSignup.setVisibility(View.GONE);
                break;
        }
    }
}