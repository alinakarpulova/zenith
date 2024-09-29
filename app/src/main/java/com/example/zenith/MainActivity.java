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

import com.example.zenith.activities.fragments.ExercisesFragment;
import com.example.zenith.activities.fragments.HistoryFragment;
import com.example.zenith.activities.fragments.HomeFragment;
import com.example.zenith.activities.fragments.MeasurementsFragment;
import com.example.zenith.activities.fragments.WorkoutsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemReselectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.nav_item_home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = new HomeFragment();
                int id = item.getItemId();
                if (id == R.id.nav_item_home) {
                    selectedFragment = new HomeFragment();
                }
                if (id == R.id.nav_item_history) {
                    selectedFragment = new HistoryFragment();
                }
                if (id == R.id.nav_item_workouts) {
                    selectedFragment = new WorkoutsFragment();
                }
                if (id == R.id.nav_item_exercises) {
                    selectedFragment = new ExercisesFragment();
                }
                if (id == R.id.nav_item_measurements){
                    selectedFragment = new MeasurementsFragment();
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.main, selectedFragment).commit();
                return true;
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    @Override
    public void onNavigationItemReselected(@NonNull MenuItem item) {

    }
}