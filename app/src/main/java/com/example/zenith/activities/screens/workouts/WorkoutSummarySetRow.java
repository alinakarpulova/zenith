package com.example.zenith.activities.screens.workouts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.zenith.R;
import com.example.zenith.models.ExerciseSet;

@SuppressLint("ViewConstructor")
public class WorkoutSummarySetRow extends TableRow {
    View view;

    public WorkoutSummarySetRow(Context context, ExerciseSet exerciseSet, int index) {
        super(context);
        init(context, exerciseSet, index);
    }

    public void init(Context context, ExerciseSet exerciseSet, int index) {
        view = LayoutInflater.from(context).inflate(R.layout.workout_summary_exercise_set_row, this, true);
        TextView setNumber = view.findViewById(R.id.workout_summary_exercise_set_index);
        setNumber.setText(String.valueOf(index));

        TextView setWeight = view.findViewById(R.id.workout_summary_exercise_set_weight);
        setWeight.setText(String.valueOf(exerciseSet.getWeight()));

        TextView setReps = view.findViewById(R.id.workout_summary_exercise_set_reps);
        setReps.setText(String.valueOf(exerciseSet.getRepetitions()));
    }
}
