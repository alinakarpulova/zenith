package com.example.zenith.activities.screens.workouts;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.zenith.R;
import com.example.zenith.controllers.DatabaseHelper;
import com.example.zenith.models.Workout;
import com.example.zenith.models.WorkoutExercise;
import com.google.android.material.button.MaterialButton;

public class WorkoutSummary extends AppCompatActivity {
    public static String WORKOUT_ID = "WORKOUT_ID";
    private Workout workout;

    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        Bundle instance = getIntent().getExtras();
        int workoutId = instance.getInt(WORKOUT_ID);

        setContentView(R.layout.workout_summary);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Previous Workout");
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        workout = databaseHelper.getWorkout(workoutId);

        TextView workoutName = findViewById(R.id.workout_summary_title);
        workoutName.setText(workout.getName());

        MaterialButton repeatButton = findViewById(R.id.workout_summary_repeat_btn);
        MaterialButton cancelButton = findViewById(R.id.workout_summary_cancel_btn);

        cancelButton.setOnClickListener((view -> {
            finish();
        }));

        LinearLayout exercisesList = findViewById(R.id.workout_summary_exercises);
        exercisesList.removeAllViews();
        for (WorkoutExercise workoutExercise : workout.getWorkoutExerciseList()) {
            exercisesList.addView(new WorkoutSummaryExerciseRow(this, workoutExercise));
        }
    }
}
