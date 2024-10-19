package com.example.zenith.activities.screens.workouts;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zenith.R;
import com.example.zenith.controllers.DatabaseHelper;
import com.example.zenith.models.Workout;

public class WorkoutSummary extends AppCompatActivity {
    public static String WORKOUT_ID = "WORKOUT_ID";
    private Workout workout;

    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        Bundle instance = getIntent().getExtras();
        int workoutId = instance.getInt(WORKOUT_ID);

        setContentView(R.layout.workout_summary);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        workout = databaseHelper.getWorkoutById(workoutId);
    }
}
