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

public class MainActivity extends AppCompatActivity {

    EditText etName;
    EditText etPassword;
    EditText etPassword2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etName = findViewById(R.id.editTextName);
        etPassword = findViewById(R.id.editTextPassword);
        etPassword2 = findViewById(R.id.editTextPassword2);
    }

    public void LoginButtonClick(View view){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void RegisterButtonClick(View view){
        String UserName = etName.getText().toString();
        String Password = etPassword.getText().toString();
        String Password2 = etPassword2.getText().toString();

        if (Password.equals(Password2)) {
            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
            saveDataToSharedPreference(UserName, Password,Password2);
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show();
        }
    }
    private void saveDataToSharedPreference(String name, String password, String password2){
        SharedPreferences sharedPreferences = getSharedPreferences("UNIQUE_FILE_NAME", MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("KEY_NAME", name);
        editor.putString("KEY_PASSWORD", password);
        editor.putString("KEY_PASSWORD2", password2);

        editor.apply();
    }
}