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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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

        signupCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = signupUsername.getText().toString().trim();
                String email = signupEmail.getText().toString().trim();
                String password = signupPassword.getText().toString().trim();
                String passwordRepeat = signupPasswordRepeat.getText().toString().trim();

                if (username.isEmpty()) {
                    signupUsername.setError("Please enter your username");
                }
                if (email.isEmpty()) {
                    signupEmail.setError("Please enter your email");
                }
                if (password.isEmpty()) {
                    signupPassword.setError("Please enter your password");
                }
                if (passwordRepeat.isEmpty() || !passwordRepeat.equals(password)) {
                    signupPasswordRepeat.setError("Password does not match!");
                } else {
                    boolean isAdmin = anAdmin.isChecked();
                    //if user is an admin
                    if (isAdmin) {
                        //Toast.makeText(RegisterActivity.this, "an admin", Toast.LENGTH_SHORT).show();
                        adminPasswordEntered = adminCodeSignup.getText().toString();
                        if(adminPasswordEntered.equals(ADMIN_PASSWORD)){
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
                if (checked)
                    //Toast.makeText(RegisterActivity.this, "Admin",Toast.LENGTH_SHORT).show();
                    accountType = "admin";
                    adminCodeSignup.setVisibility(View.VISIBLE);
                    break;
            case R.id.adminNo:
                if (checked)
                    //Toast.makeText(RegisterActivity.this, "Not Admin",Toast.LENGTH_SHORT).show();
                    accountType = "user";
                    adminCodeSignup.setVisibility(View.GONE);
                    break;
        }
    }
}