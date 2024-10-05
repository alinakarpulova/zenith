package com.example.zenith.activities.screens.workouts;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.zenith.R;

public class WorkoutRow extends ConstraintLayout {

    public WorkoutRow(@NonNull Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        // Inflate the layout for this view
        LayoutInflater.from(context).inflate(R.layout.workout_exercise_row, this, true);

    }

}
