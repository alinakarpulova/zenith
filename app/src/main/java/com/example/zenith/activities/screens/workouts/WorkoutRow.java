package com.example.zenith.activities.screens.workouts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.zenith.R;
import com.example.zenith.models.ExerciseSet;
import com.example.zenith.models.WorkoutExercise;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.color.MaterialColors;

@SuppressLint("ViewConstructor")
public class WorkoutRow extends ConstraintLayout {
    private View view;
    private final WorkoutExercise workoutExercise;
    private TableLayout setLayout;
    private TextView exerciseNameText;
    private MaterialButton addSetButton;
    private ImageButton deleteButton;

    public WorkoutRow(@NonNull Context context, WorkoutExercise workoutExercise, OnRemoveListener onRemoveListener) {
        super(context);
        this.workoutExercise = workoutExercise;
        init(context, onRemoveListener);
    }

    private void init(Context context, OnRemoveListener onRemoveListener) {
        // Inflate the layout for this view
        view = LayoutInflater.from(context).inflate(R.layout.workout_exercise_row, this, true);

        exerciseNameText = findViewById(R.id.workout_exercise_row_name);
        exerciseNameText.setText(workoutExercise.getExercise().getName());

        setLayout = findViewById(R.id.workout_exercise_grid);
        updateView();

        deleteButton = findViewById(R.id.delete_workout_exercise_btn);
        deleteButton.setColorFilter(MaterialColors.getColor(view, com.google.android.material.R.attr.colorOnSurface));
        deleteButton.setOnClickListener((view) -> {
            if (!workoutExercise.getExerciseSets().isEmpty()) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete Confirmation")
                        .setMessage("Are you sure you want to remove this exercise?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                onRemoveListener.onRemove();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            } else {
                onRemoveListener.onRemove();
            }

        });

        addSetButton = findViewById(R.id.workout_exercise_add_set_btn);
        addSetButton.setOnClickListener((view) -> {
            workoutExercise.getExerciseSets().add(new ExerciseSet(0, 0f));
            updateView();
        });

    }

    private void removeSet(int index) {
        workoutExercise.removeSetByIndex(index);
    }


    private void updateView() {
        setLayout.removeAllViews();
        int index = 0;
        for (ExerciseSet exerciseSet : workoutExercise.getExerciseSets()) {
            int currentIndex = index;  // Keep track of the current index for callback
            index += 1;
            setLayout.addView(new WorkoutSetRow(this.getContext(), exerciseSet, index, () -> {
                removeSet(currentIndex);
                updateView();
            }));
        }
    }

    public interface OnRemoveListener {
        void onRemove();
    }

}
