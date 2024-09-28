package com.fit2081.assignment1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.fit2081.assignment1.provider.CategoryViewModel;
import com.fit2081.assignment1.provider.EventViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class NewEventForm extends AppCompatActivity {
    MyRecyclerAdapter recyclerAdapter;
    private RecyclerView recyclerView;
    private NavigationView navigationView;
    RecyclerView.LayoutManager layoutManager;
    EditText etEventId;
    Gson gson = new Gson();
    ArrayList<EventCategory> CategoryList = new ArrayList<>();
    ArrayList<com.fit2081.assignment1.Event> EventList = new ArrayList<>();
    EditText etEventName;
    EditText etCatId;
    EditText etTixAvailable;
    Switch isActiveSwitch2;
    private GestureDetector gestureDetector;
    private CategoryViewModel categoryViewModel;
    private EventViewModel eventViewModel;
    DrawerLayout drawerlayout;

    private Event lastSavedEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.drawer_layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etEventId = findViewById(R.id.editTextText);
        etEventName = findViewById(R.id.editTextText2);
        etCatId = findViewById(R.id.editTextText3);
        etTixAvailable = findViewById(R.id.editTextNumber);
        isActiveSwitch2 = findViewById(R.id.switch2);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        Toolbar myToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolBar);

        drawerlayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerlayout, myToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerlayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new MyNavigationListener());

        getSupportFragmentManager().beginTransaction().replace(
                R.id.frame2, new FragmentListCategory()).commit();

        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveEventButtonClick();
            }
        });

        // Set up the GestureDetector
        gestureDetector = new GestureDetector(this, new GestureListener());

        // Find the touchpad view and set a touch listener
        View touchpad = findViewById(R.id.touchpad);
        touchpad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(@NonNull MotionEvent e) {
            SaveEventButtonClick();
            return super.onDoubleTap(e);
        }

        @Override
        public void onLongPress(@NonNull MotionEvent e) {
            clearFields();
            super.onLongPress(e);
        }
    }
    public void SaveEventButtonClick() {
        String EventId = generateAlphaNumeric();
        String EventName = etEventName.getText().toString();
        String CatId = etCatId.getText().toString();
        String TixAvailable = etTixAvailable.getText().toString();
        int TixA = 0;
        boolean isActive2 = isActiveSwitch2.isChecked();

        if (EventName.isEmpty() || CatId.isEmpty()) {
            Toast.makeText(this, "Event name and Category id required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (EventName.matches("[0-9]+")) {
            Toast.makeText(this, "Invalid event name", Toast.LENGTH_SHORT).show();
            return;
        }
        for (char eventNameChar : EventName.toCharArray()) {
            if (!Character.isLetterOrDigit(eventNameChar) && eventNameChar != ' ') {
                Toast.makeText(this, "Invalid event name", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (!TixAvailable.isEmpty()) {
            try {
                final int finalTixA = Integer.parseInt(TixAvailable);
                LiveData<EventCategory> categoryLiveData = categoryViewModel.getCategoryById(CatId);
                categoryLiveData.observe(this, new Observer<EventCategory>() {
                    @Override
                    public void onChanged(EventCategory eventCategory) {
                        if (eventCategory != null) {
                            // Category exists
                            eventCategory.setEventCount(eventCategory.getEventCount() + 1); // Increment EventCount
                            categoryViewModel.update(eventCategory); // Update Category record

                            // Create new Event
                            com.fit2081.assignment1.Event eventz = new com.fit2081.assignment1.Event(EventId, EventName, CatId, finalTixA, isActive2);
                            eventViewModel.insert(eventz); // Save the Event record
                            etEventId.setText(EventId);
                            // Show a message indicating event saved successfully
                            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Event saved successfully: " + EventId + " to " + CatId, Snackbar.LENGTH_LONG)
                                    .setAction("UNDO", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            // Undo saving the event
                                            undoSaveEvent();
                                        }
                                    });
                            snackbar.show();
                            lastSavedEvent = eventz;
                        } else {
                            // Category does not exist
                            Toast.makeText(NewEventForm.this, "Category id does not exist", Toast.LENGTH_SHORT).show();
                        }
                        // Remove observer
                        categoryLiveData.removeObserver(this);
                    }
                });
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid tickets available", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void undoSaveEvent() {
        eventViewModel.undo(); // Delete the last saved event

        // Show a message indicating event deletion
        Toast.makeText(NewEventForm.this, "Last event removed", Toast.LENGTH_SHORT).show();

    }
    class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // get the id of the selected item
            int id = item.getItemId();

            if (id == R.id.item_menu_1) {
                Intent intent = new Intent(NewEventForm.this, ListCategoryActivity.class);
                startActivity(intent);
            } else if (id == R.id.item_menu_2) {
                Intent intent = new Intent(NewEventForm.this, NewCatForm.class);
                startActivity(intent);
            } else if (id == R.id.item_menu_3) {
                Intent intent = new Intent(NewEventForm.this, ListEventActivity.class);
                startActivity(intent);
            } else if (id == R.id.item_menu_4) {
                Intent intent = new Intent(NewEventForm.this, Login.class);
                startActivity(intent);
                finish();
            }
            drawerlayout.closeDrawers();
            return true;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int temp = 0;
        if (item.getItemId() == R.id.option_refresh) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame2, new FragmentListCategory()).commit();
        } else if (item.getItemId() == R.id.option_clear) {
            clearFields();
        } else if (item.getItemId() == R.id.option_del_cat) {
            CategoryList.clear(); ;
            categoryViewModel.deleteAll();
//            addCatToSharedPreference();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame2, new FragmentListCategory()).commit();
        } else if (item.getItemId() == R.id.option_del_event) {
//            readCategoryFromShared();
//            readEventFromShared();
//            for (EventCategory categoryIdsRestored1 : CategoryList) {
//                for (Event eventCatIdsRestored : EventList) {
//                    if (categoryIdsRestored1.getCategoryID().equals(eventCatIdsRestored.getCatId())){
//                        categoryIdsRestored1.setEventCount(categoryIdsRestored1.getEventCount()-1);
//                    }
//                }
//            }
            EventList.clear();
//            addEventToSharedPreference2();
//            addCatToSharedPreference();
            eventViewModel.deleteAll();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame2, new FragmentListCategory()).commit();
        }
        return true;
    }

    private void clearFields() {
        etEventId.setText("");
        etEventName.setText("");
        etCatId.setText("");
        etTixAvailable.setText("");
        isActiveSwitch2.setChecked(false);
    }

    public static String generateAlphaNumeric() {
        char firstAlpha = generateRandomAlphabet();
        char secondAlpha = generateRandomAlphabet();
        String digits = generateRandomDigits(5);

        String alphaNumeric = "E" + firstAlpha + secondAlpha + "-" + digits;
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