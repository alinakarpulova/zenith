package com.example.zenith.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zenith.R;
import com.example.zenith.models.Exercise;

public class ExerciseRowAdapter extends BaseAdapter {
    Context context;
    Exercise[] exercises;
    private static LayoutInflater layoutInflater = null;

    public ExerciseRowAdapter(Context context, Exercise[] exercises) {
        this.context = context;
        this.exercises = exercises;
        layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return exercises.length;
    }

    @Override
    public Object getItem(int i) {
        return exercises[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View vi = view;
        if (vi == null) {
            vi = layoutInflater.inflate(R.layout.exercise_row_item, null);
        }
        TextView name = (TextView) vi.findViewById(R.id.exercise_name);
        TextView category = (TextView) vi.findViewById(R.id.exercise_category);

        name.setText(exercises[position].getName());
        category.setText(exercises[position].getExerciseCategory().toString());
        return vi;
    }
}
