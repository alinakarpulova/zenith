package com.example.zenith.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.zenith.R;
import com.example.zenith.models.Workout;

import java.util.ArrayList;

public class SimpleWorkoutAdapter extends ArrayAdapter<Workout> {
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
        TextView startTime = convertView.findViewById(R.id.workout_start_time);
        TextView endTime = convertView.findViewById(R.id.workout_end_time);

        name.setText(workout.getName());
        startTime.setText(workout.getStartTime().toString());
        endTime.setText(workout.getEndTime().toString());
        return convertView;
    }
}
