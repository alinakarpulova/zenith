package com.example.zenith.activities.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zenith.R;
import com.example.zenith.models.Exercise;

public class ExerciseRowAdapter extends RecyclerView.Adapter<ExerciseRowAdapter.ViewHolder> {
    Exercise[] exercises;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView label;
        private final ImageView image;

        public ViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.exercise_name);
            label = view.findViewById(R.id.exercise_category);
            image = view.findViewById(R.id.exercise_img);
        }

        public TextView getLabel() {
            return label;
        }

        public TextView getName() {
            return name;
        }

        public ImageView getImage() {
            return image;
        }
    }

    public ExerciseRowAdapter(Exercise[] exercises) {
        this.exercises = exercises;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.exercise_row_item, viewGroup, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getName().setText(exercises[position].getName());
        viewHolder.getLabel().setText(exercises[position].getExerciseCategory().toString());
    }

    @Override
    public long getItemId(int i) {
        return exercises[i].getId();
    }

    @Override
    public int getItemCount() {
        return exercises.length;
    }
}
