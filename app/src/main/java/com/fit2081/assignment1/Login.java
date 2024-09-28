package com.fit2081.assignment1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {

    EditText etUser;
    EditText etPW;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SharedPreferences sharedPreferences = getSharedPreferences("UNIQUE_FILE_NAME", MODE_PRIVATE);
        String NameRestored = sharedPreferences.getString("KEY_NAME", "");
        etUser = findViewById(R.id.editTextLogName);
        etPW = findViewById(R.id.editTextLogPass);
        etUser.setText(NameRestored);
    }

    public void LogButtonClick(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("UNIQUE_FILE_NAME", MODE_PRIVATE);

        String NameRestored = sharedPreferences.getString("KEY_NAME", "");
        String PasswordRestored = sharedPreferences.getString("KEY_PASSWORD", "");

        String enteredUsername = etUser.getText().toString();
        String enteredPassword = etPW.getText().toString();

        if (NameRestored.equals(enteredUsername) && PasswordRestored.equals(enteredPassword)) {
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, NewEventForm.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Either username or password is incorrect", Toast.LENGTH_SHORT).show();
        }
    }
    public void LogRegButtonClick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
