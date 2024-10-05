package com.example.zenith.activities.screens.workouts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TableRow;

import com.example.zenith.R;
import com.example.zenith.models.ExerciseSet;

@SuppressLint("ViewConstructor")
public class WorkoutSetRow extends TableRow {
    private ExerciseSet exerciseSet;

    public WorkoutSetRow(Context context) {
        super(context);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.workout_exercise_set_row, this, true);

    }
}
