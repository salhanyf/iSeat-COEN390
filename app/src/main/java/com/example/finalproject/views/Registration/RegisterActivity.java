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
import com.google.firebase.database.DataSnapshot;

public class RegisterActivity extends AppCompatActivity {
    private final String ADMIN_PASSWORD = "admin";

    private FirebaseAuth auth;
    private EditText signupUsername, signupEmail, signupPassword, signupPasswordRepeat, adminCodeSignup;
    private RadioButton anAdmin, notAnAdmin;
    private Button signupCreateAccountButton, loginRedirectButton;
//    private String accountType = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            Toast.makeText(this, "User " + auth.getCurrentUser().getEmail() + " already signed in!", Toast.LENGTH_SHORT).show();
            finish();
        }

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
            finish();
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });

        signupCreateAccountButton.setOnClickListener(v -> {
            String username = signupUsername.getText().toString().trim();
            String email = signupEmail.getText().toString().trim();
            String password = signupPassword.getText().toString().trim();
            String passwordRepeat = signupPasswordRepeat.getText().toString().trim();
            String adminPasswordEntered = adminCodeSignup.getText().toString();

            if (!checkErrors(username, email, password, passwordRepeat, adminPasswordEntered))
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new RegisterSuccessOnCompleteListener(email, password, anAdmin.isChecked()));
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
        switch(view.getId()) {
            case R.id.adminYes:
                if (checked)
                    //Toast.makeText(RegisterActivity.this, "Admin",Toast.LENGTH_SHORT).show();
//                    accountType = "admin";
                    adminCodeSignup.setVisibility(View.VISIBLE);
                    break;
            case R.id.adminNo:
                if (checked)
                    //Toast.makeText(RegisterActivity.this, "Not Admin",Toast.LENGTH_SHORT).show();
//                    accountType = "user";
                    adminCodeSignup.setVisibility(View.GONE);
                    break;
        }
    }

    private class RegisterSuccessOnCompleteListener implements OnCompleteListener<AuthResult> {
        final String email, password;
        final boolean adminFlag;

        RegisterSuccessOnCompleteListener(String email, String password, boolean adminFlag) {
            super();
            this.email = email;
            this.password = password;
            this.adminFlag = adminFlag;
        }

        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
                if (adminFlag) {
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(t -> {
                        if (t.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "User Registration & Login Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, WelcomeActivity.class));
//                            finishAffinity();
                        } else {
                            Toast.makeText(RegisterActivity.this, "User Registration Success but Login Failed", Toast.LENGTH_SHORT).show();
//                            finishAffinity();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        }
                    });
                } else {
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(t -> {
                        if (t.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "User Registration & Login Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, WelcomeActivity.class));
//                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "User Registration Success but Login Failed", Toast.LENGTH_SHORT).show();
//                            finishAffinity();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        }
                    });
                }
            } else {
                Toast.makeText(RegisterActivity.this, "User Registration Failed, Try Again", Toast.LENGTH_SHORT).show();
            }
        }
    }
}