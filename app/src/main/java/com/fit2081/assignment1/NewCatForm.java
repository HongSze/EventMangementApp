package com.fit2081.assignment1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.metrics.Event;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.fit2081.assignment1.provider.CategoryViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class NewCatForm extends AppCompatActivity {
    EditText etCatId;
    Gson gson = new Gson();
    ArrayList<EventCategory> CatList = new ArrayList<>();
    EditText etCatName;
    EditText etEventCount;
    Switch isActiveSwitch;
    EditText etLoc;
    private CategoryViewModel categoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_cat_form);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etCatId = findViewById(R.id.editTextCatId);
        etCatName = findViewById(R.id.editTextCatName);
        etEventCount = findViewById(R.id.editTextEventCount);
        isActiveSwitch = findViewById(R.id.switch1);
        etLoc = findViewById(R.id.editTextLoc);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
//        categoryViewModel.getAllCategories().observe(this, newData -> {
//            // cast List<Student> to ArrayList<Student>
//            adapter.setData(new ArrayList<EventCategory>(newData));
//            adapter.notifyDataSetChanged();
//        });
    }

    private void addCatToSharedPreference(EventCategory category){
        SharedPreferences sharedPreferences = getSharedPreferences("CATEGORY", MODE_PRIVATE);
        String json = sharedPreferences.getString("CATEGORY_DETAILS", null);
        if (json != null) {
            Type type = new TypeToken<ArrayList<EventCategory>>() {}.getType();
            CatList = gson.fromJson(json, type);
        }
        CatList.add(category);
        String newCatList = gson.toJson(CatList);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("CATEGORY_DETAILS", newCatList);
        edit.apply();
    }

    public void SaveCatButtonClick(View view){
        String CatId = generateAlphaNumeric();
        String CatName = etCatName.getText().toString().trim();
        String EventCount = etEventCount.getText().toString();
        int ECount = 0;
        boolean isActive = isActiveSwitch.isChecked();
        String Location = etLoc.getText().toString();

        if (CatName.isEmpty()){
            Toast.makeText(this, "Category name required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (CatName.matches("[0-9]+")){
            Toast.makeText(this, "Invalid category name", Toast.LENGTH_SHORT).show();
            return;
        }
        for (char categoryNameChar : CatName.toCharArray()) {
            if (!Character.isLetterOrDigit(categoryNameChar) && categoryNameChar != ' ') {
                Toast.makeText(this, "Invalid category name", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (!EventCount.isEmpty()) {
            try {
                ECount = Integer.parseInt(EventCount);
            } catch (NumberFormatException e) {
                // Handle the case where EventCount is not a valid integer
                // Show error message or handle it appropriately
                Toast.makeText(this, "Invalid event count", Toast.LENGTH_SHORT).show();
                return; // Exit the method
            }
        }

        etCatId.setText(CatId);
        EventCategory categoryz = new EventCategory(CatId, CatName, ECount, isActive, Location);
        //addCatToSharedPreference(category);
        Toast.makeText(this, "Category saved successfully: "+ CatId, Toast.LENGTH_SHORT).show();
        categoryViewModel.insert(categoryz);
        finish();
    }


    public static String generateAlphaNumeric() {
        char firstAlpha = generateRandomAlphabet();
        char secondAlpha = generateRandomAlphabet();
        String digits = generateRandomDigits(4);

        String alphaNumeric = "C" + firstAlpha + secondAlpha + "-" + digits;
        return alphaNumeric;
    }
    public static char generateRandomAlphabet() {
        Random random = new Random();
        char alphabet = (char) (random.nextInt(26) + 'A');
        return alphabet;
    }

    public static String generateRandomDigits(int length) {
        Random random = new Random();
        StringBuilder digits = new StringBuilder();
        for (int i = 0; i < length; i++) {
            digits.append(random.nextInt(10));
        }
        return digits.toString();
    }
}


