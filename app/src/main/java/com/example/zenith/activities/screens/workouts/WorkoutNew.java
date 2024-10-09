package com.example.zenith.activities.screens.workouts;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.zenith.R;
import com.example.zenith.components.DialogWithSearchMulti;
import com.example.zenith.controllers.DatabaseHelper;
import com.example.zenith.models.Exercise;
import com.example.zenith.models.Workout;
import com.example.zenith.models.WorkoutExercise;
import com.google.android.material.button.MaterialButton;

import java.util.Collections;
import java.util.List;

public class WorkoutNew extends AppCompatActivity {
    Workout workout = new Workout("New Workout");
    MaterialButton addExerciseButton;
    LinearLayout exerciseList;

    List<Exercise> exercises;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        setContentView(R.layout.workout_new);

        exercises = new DatabaseHelper(this).getExerciseList();

        addExerciseButton = findViewById(R.id.add_exercise_btn);
        exerciseList = findViewById(R.id.workout_exercise_list);

        DialogWithSearchMulti exercisesDialog = new DialogWithSearchMulti(exercises, this);

        addExerciseButton.setOnClickListener((view) -> {
            exercisesDialog.showDialog(Collections.emptyList());
        });
        exercisesDialog.getDialog().setOnDismissListener((dialogInterface -> {
            for (Exercise exercise : exercisesDialog.getSelectedItems()) {
                if (!workout.containsExercise(exercise)) {
                    WorkoutExercise workoutExercise = new WorkoutExercise(exercise);
                    workout.addWorkoutExercise(workoutExercise);
                }
            }
            updateExerciseListView();
        }));
    }

    private void removeWorkoutExercise(WorkoutExercise workoutExercise) {
        workout.removeWorkoutExercise(workoutExercise);
        updateExerciseListView();
    }

    private void updateExerciseListView() {
        exerciseList.removeAllViews();
        for (WorkoutExercise workoutExercise : workout.getWorkoutExerciseList()) {
            exerciseList.addView(new WorkoutRow(WorkoutNew.this, workoutExercise, () -> {
                removeWorkoutExercise(workoutExercise);
            }));
        }
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

