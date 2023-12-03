package com.example.mainproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    Button applyButton;
    EditText firstName, lastName, userNameReg, passwordReg, verifyPasswordReg;
    DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        userNameReg = (EditText) findViewById(R.id.userNameReg);
        passwordReg = (EditText) findViewById(R.id.passwordReg);
        verifyPasswordReg = (EditText) findViewById(R.id.verifyPasswordReg);

        applyButton = (Button) findViewById(R.id.applyButton);

        dbManager = new DBManager(Register.this);
        dbManager.open();

        // Create new account.
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = userNameReg.getText().toString();
                String password = passwordReg.getText().toString();
                String verify = verifyPasswordReg.getText().toString();
                String first = firstName.getText().toString();
                String last = lastName.getText().toString();

                if (password.equals(verify)) {
                    Toast.makeText(Register.this, "Register successful", Toast.LENGTH_SHORT).show();
                    dbManager.insert(userName, password, first, last);
                    Intent myIntent = new Intent(Register.this, Login.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(myIntent);
                } else {
                    Toast.makeText(Register.this, "Invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}