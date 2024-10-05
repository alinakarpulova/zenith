package com.example.zenith.activities.screens.workouts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.zenith.R;
import com.example.zenith.models.WorkoutExercise;
import com.google.android.material.button.MaterialButton;

@SuppressLint("ViewConstructor")
public class WorkoutRow extends ConstraintLayout {
    private final WorkoutExercise workoutExercise;
    private TableLayout setLayout;
    private TextView exerciseNameText;
    private MaterialButton addSetButton;

    public WorkoutRow(@NonNull Context context, WorkoutExercise workoutExercise) {
        super(context);
        this.workoutExercise = workoutExercise;
        init(context);
    }

    private void init(Context context) {
        // Inflate the layout for this view
        LayoutInflater.from(context).inflate(R.layout.workout_exercise_row, this, true);

        exerciseNameText = findViewById(R.id.workout_exercise_row_name);
        exerciseNameText.setText(workoutExercise.getExercise().getName());

        setLayout = findViewById(R.id.workout_exercise_grid);

        addSetButton = findViewById(R.id.workout_exercise_add_set_btn);
        addSetButton.setOnClickListener((view) -> {
            setLayout.addView(new WorkoutSetRow(this.getContext()), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        });

    }

}
