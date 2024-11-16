package com.example.zenith.activities.screens.workouts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zenith.R;
import com.example.zenith.models.WorkoutExercise;

@SuppressLint("ViewConstructor")
public class WorkoutExerciseRow extends LinearLayout {
    public WorkoutExerciseRow(Context context, WorkoutExercise workoutExercise) {

        super(context);
        init(context, workoutExercise);
    }

    private void init(Context context, WorkoutExercise workoutExercise) {
        View view = LayoutInflater.from(context).inflate(R.layout.workout_row_exercise, this, true);

        TextView name = view.findViewById(R.id.workout_row_exercise_name);
        TextView sets = view.findViewById(R.id.workout_row_exercise_sets);

        name.setText(workoutExercise.getExercise().getName());
        String setsText = workoutExercise.getExerciseSets().size() + "x" + workoutExercise.getExerciseSets().get(0).getRepetitions();
        sets.setText(setsText);
    }
}
