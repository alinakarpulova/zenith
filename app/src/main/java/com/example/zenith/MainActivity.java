package com.example.zenith;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.zenith.activities.screens.HomeFragment;
import com.example.zenith.activities.screens.exercises.ExercisesFragment;
import com.example.zenith.activities.screens.workouts.WorkoutsFragment;
import com.example.zenith.controllers.DatabaseHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemReselectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Time Backport for API 24 support
        AndroidThreeTen.init(this);

        DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
        try {
            databaseHelper.copyDatabaseIfNeeded(); // Prepopulate database with exercise data
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.nav_item_home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Default Fragment
                Fragment selectedFragment = new HomeFragment();
                int id = item.getItemId();
                if (id == R.id.nav_item_home) {
                    selectedFragment = new HomeFragment();
                }
                if (id == R.id.nav_item_workouts) {
                    selectedFragment = new WorkoutsFragment();
                }
                if (id == R.id.nav_item_exercises) {
                    selectedFragment = new ExercisesFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.main, selectedFragment).commit();
                return true;
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right,0);
            return insets;
        });
    }


    @Override
    public void onNavigationItemReselected(@NonNull MenuItem item) {

    }
}