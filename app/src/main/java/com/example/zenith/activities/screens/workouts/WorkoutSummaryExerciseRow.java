package com.example.zenith.activities.screens.workouts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.zenith.R;
import com.example.zenith.models.ExerciseSet;
import com.example.zenith.models.WorkoutExercise;

@SuppressLint("ViewConstructor")
public class WorkoutSummaryExerciseRow extends LinearLayout {
    public WorkoutSummaryExerciseRow(Context context, WorkoutExercise workoutExercise) {
        super(context);
        init(context, workoutExercise);
    }

    public void init(Context context, WorkoutExercise workoutExercise) {
        View view = LayoutInflater.from(context).inflate(R.layout.workout_summary_exercise_row, this, true);
        TextView name = view.findViewById(R.id.workout_summary_exercise_name);
        name.setText(workoutExercise.getExercise().getName());

        TableLayout tableLayout = view.findViewById(R.id.workout_summary_exercise_set_table);
        int index = 1;
        for (ExerciseSet exerciseSet : workoutExercise.getExerciseSets()) {
            tableLayout.addView(new WorkoutSummarySetRow(context, exerciseSet, index));
            index++;
        }
    }
}
