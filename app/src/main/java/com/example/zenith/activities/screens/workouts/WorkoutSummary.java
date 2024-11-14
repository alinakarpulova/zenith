package com.example.zenith.activities.screens.workouts;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

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
        MaterialButton deleteButton = findViewById(R.id.workout_summary_delete_btn);

        repeatButton.setOnClickListener((view -> {
            Intent intent = new Intent(this, WorkoutNew.class);
            intent.putExtra(WorkoutSummary.WORKOUT_ID, workout.getId());
            finish();
            startActivity(intent);
        }));

        cancelButton.setOnClickListener((view -> {
            finish();
        }));

        deleteButton.setOnClickListener((view -> {
            AlertDialog alertDialog = new AlertDialog.Builder(WorkoutSummary.this)
                    .setTitle("Delete Confirmation")
                    .setMessage("Are you sure you want to delete this workout?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            databaseHelper.deleteWorkout(workoutId);
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
            // Change the button colors after the dialog is shown
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorError));
        }));

        LinearLayout exercisesList = findViewById(R.id.workout_summary_exercises);
        exercisesList.removeAllViews();
        for (WorkoutExercise workoutExercise : workout.getWorkoutExerciseList()) {
            exercisesList.addView(new WorkoutSummaryExerciseRow(this, workoutExercise));
        }
    }
}
