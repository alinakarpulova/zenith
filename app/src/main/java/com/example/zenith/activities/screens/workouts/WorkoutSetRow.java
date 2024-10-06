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
public class WorkoutSetRow extends TableRow {
    private ExerciseSet exerciseSet;
    private int setIndex;
    private TextView setIndexText;

    public WorkoutSetRow(Context context, ExerciseSet exerciseSet, int setIndex) {
        super(context);
        this.exerciseSet = exerciseSet;
        this.setIndex = setIndex;
        init(context);
    }

    public void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.workout_exercise_set_row, this, true);
        setIndexText = view.findViewById(R.id.set_index);
        setIndexText.setText(String.valueOf(setIndex));
    }
}
