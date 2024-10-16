package com.example.zenith.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.zenith.R;
import com.example.zenith.activities.screens.workouts.WorkoutExerciseRow;
import com.example.zenith.models.Workout;
import com.example.zenith.models.WorkoutExercise;

import org.threeten.bp.Duration;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class SimpleWorkoutAdapter extends ArrayAdapter<Workout> {
    private LinearLayout exerciseView;

    public SimpleWorkoutAdapter(@NonNull Context context, ArrayList<Workout> workouts) {
        super(context, 0, workouts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Workout workout = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.workout_row, parent, false);
        }
        TextView name = convertView.findViewById(R.id.workout_name);

        NumberFormat formatter = new DecimalFormat("00");
        TextView durationTxt = convertView.findViewById(R.id.workout_duration);
        Duration duration = Duration.between(workout.getStartTime(), workout.getEndTime());
        String durationStr = formatter.format(duration.toHoursPart()) + ":"
                + formatter.format(duration.toMinutesPart()) + ":"
                + formatter.format(duration.toSecondsPart());
        durationTxt.setText(durationStr);

        exerciseView = convertView.findViewById(R.id.workout_row_exercises);
        // Add exercises to this view
        exerciseView.removeAllViews();
        for (WorkoutExercise workoutExercise : workout.getWorkoutExerciseList()) {
            // Create view and add it to list
            exerciseView.addView(new WorkoutExerciseRow(getContext(), workoutExercise));
        }

        name.setText(workout.getName());
        return convertView;
    }
}
