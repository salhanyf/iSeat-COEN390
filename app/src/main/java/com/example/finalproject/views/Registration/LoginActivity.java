package com.example.finalproject.views.Registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproject.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText loginEmail, loginPassword;
    private Button loginButton, signupRedirectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            Toast.makeText(this, "User " + auth.getCurrentUser().getEmail() + " already signed in!", Toast.LENGTH_SHORT).show();
            finish();
        }

        loginEmail = findViewById(R.id.emailLogin);
        loginPassword = findViewById(R.id.passwordLogin);
        loginButton = findViewById(R.id.loginButton);
        signupRedirectButton = findViewById(R.id.signupRedirect);

        signupRedirectButton.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        });

        loginButton.setOnClickListener(v -> {
            String email = loginEmail.getText().toString();
            String password = loginPassword.getText().toString();

            if (!checkErrors(email, password)) {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private boolean checkErrors(String email, String password) {
        boolean err = false;
        if (email.isEmpty()) {
            loginEmail.setError("Please Enter Email");
            err = true;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loginEmail.setError("Please Enter Valid Email");
            err = true;
        }
        if (password.isEmpty()) {
            loginPassword.setError("Please Enter Password");
            err = true;
        }
        return err;
    }
}