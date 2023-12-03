package com.example.mainproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    Button loginButton, registerButton;
    EditText userName, password;
    DatabaseHelper databaseHelper;
    public static final String USERNAME = "currentUsername";
    public static final String SHARED_PREFS = "sharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);

        loginButton = (Button) findViewById(R.id.loginButton);
        registerButton = (Button) findViewById(R.id.registerButton);

        databaseHelper = new DatabaseHelper(Login.this);

        // Login button here.
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = userName.getText().toString();
                String pass = password.getText().toString();

                // Check if the username and password are correct
                if (validateLogin(user, pass)) {
                    // Save the username in SharedPreferences upon successful login
                    saveUsernameToSharedPreferences(user);

                    // Successful login
                    Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Login.this, DiscussionRoom.class);
                    passIntent(intent, user);
                    startActivity(intent);
                } else {
                    // Invalid login
                    Toast.makeText(Login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Go to register.
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Login.this, Register.class);
                startActivity(myIntent);
            }
        });
    }

    // Shared Preference so that it would not forget the username in the session.
    private void saveUsernameToSharedPreferences(String username) {
        // Get SharedPreferences instance
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        // Use an editor to modify SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Save the username with a unique key
        editor.putString(USERNAME, username);

        // Apply the changes
        editor.apply();
    }

    // Validate the login credentials against the database
    private boolean validateLogin(String username, String password) {
        return databaseHelper.checkCredentials(username, password);
    }

    // Pass additional intent.
    public Intent passIntent(Intent myIntent, String user) {
        myIntent.putExtra("username", user);
        return myIntent;
    }
}