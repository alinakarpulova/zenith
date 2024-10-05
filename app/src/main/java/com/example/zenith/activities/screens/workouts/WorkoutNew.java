package com.example.zenith.activities.screens.workouts;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.zenith.R;
import com.example.zenith.models.Workout;
import com.google.android.material.button.MaterialButton;

public class WorkoutNew extends AppCompatActivity {
    Workout workout = new Workout("New Workout");
    MaterialButton addExerciseButton;
    LinearLayout exerciseList;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        setContentView(R.layout.workout_new);

        addExerciseButton = findViewById(R.id.add_exercise_btn);
        exerciseList = findViewById(R.id.workout_exercise_list);

        addExerciseButton.setOnClickListener((view) -> {
            exerciseList.addView(new WorkoutRow(this));
        });

    }


    @Override
    public void onBackPressed() {
        // Temporary prevent exit from active workout
        new AlertDialog.Builder(this)
                .setTitle("Exit Confirmation")
                .setMessage("Are you sure you want cancel the workout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // If the user confirms, allow the activity to close
                        WorkoutNew.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, WorkoutNew.class);
    }

}

