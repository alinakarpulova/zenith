package com.example.zenith.activities.screens.workouts;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.zenith.R;
import com.example.zenith.components.DialogWithSearchMulti;
import com.example.zenith.controllers.DatabaseHelper;
import com.example.zenith.models.Exercise;
import com.example.zenith.models.Workout;
import com.example.zenith.models.WorkoutExercise;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Collections;
import java.util.List;

public class WorkoutNew extends AppCompatActivity {
    public static String WORKOUT_ID = "WORKOUT_ID";

    Workout workout;
    MaterialButton addExerciseButton;
    MaterialButton cancelWorkoutButton;
    MaterialButton finishWorkoutButton;
    LinearLayout exerciseList;
    TextInputEditText workoutNameText;

    List<Exercise> exercises;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.workout_new);

        Bundle instance = getIntent().getExtras();
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        if (instance != null) {
            int workoutId = instance.getInt(WORKOUT_ID);
            workout = dbHelper.getWorkout(workoutId);
            workout.restart();
        } else {
            workout = new Workout("New Workout");
        }
        exercises = dbHelper.getExerciseList();

        cancelWorkoutButton = findViewById(R.id.cancel_workout_btn);
        addExerciseButton = findViewById(R.id.add_exercise_btn);
        exerciseList = findViewById(R.id.workout_exercise_list);
        finishWorkoutButton = findViewById(R.id.workout_complete_btn);
        workoutNameText = findViewById(R.id.workout_name_txt);
        updateExerciseListView();

        setupListeners(dbHelper);
    }

    private void setupListeners(DatabaseHelper dbHelper) {
        DialogWithSearchMulti<Exercise> exercisesDialog = new DialogWithSearchMulti<>(exercises, this);
        workoutNameText.setText(workout.getName());

        cancelWorkoutButton.setOnClickListener((view) -> {
            cancelWorkout();
        });

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

        workoutNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                workout.setName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        finishWorkoutButton.setOnClickListener((view) -> {
            dbHelper.saveWorkout(workout);
            finish();
        });
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

    private void cancelWorkout() {
        AlertDialog dialog = new AlertDialog.Builder(this)
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
        // Change the button colors after the dialog is shown
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorError));
    }

    @Override
    public void onBackPressed() {
        cancelWorkout();
    }


    public static Intent makeIntent(Context context) {
        return new Intent(context, WorkoutNew.class);
    }

}

