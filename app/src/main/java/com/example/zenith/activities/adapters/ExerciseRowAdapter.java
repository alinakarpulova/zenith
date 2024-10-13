package com.example.zenith.activities.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zenith.R;
import com.example.zenith.activities.screens.exercises.ExerciseDetails;
import com.example.zenith.models.Exercise;

import java.util.ArrayList;
import java.util.List;

public class ExerciseRowAdapter extends RecyclerView.Adapter<ExerciseRowAdapter.ViewHolder> implements Filterable {
    Exercise[] exercises;
    private Filter filter;
    private List<Exercise> filteredExercises; // List for filtered results
    private Context context;

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

    public ExerciseRowAdapter(Exercise[] exercises, Context context) {
        this.exercises = exercises;
        this.filteredExercises = new ArrayList<>(List.of(exercises));
        this.context = context;
    }

    // Interface for click events
    public interface OnExerciseClickListener {
        void onExerciseClick(Exercise exercise); // Method called when an item is clicked
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
        viewHolder.getName().setText(filteredExercises.get(position).getName());
        viewHolder.getLabel().setText(filteredExercises.get(position).getExerciseCategory().toString());

        // Set click listener on the itemView
        viewHolder.itemView.setOnClickListener(v -> {
            // Start the new activity
            Intent intent = new Intent(context, ExerciseDetails.class);
            // Pass necessary data (e.g., exercise ID or other data)
            intent.putExtra(ExerciseDetails.EXERCISE_ID, filteredExercises.get(position).getId());
            context.startActivity(intent);  // Start the activity
        });
    }


    @Override
    public long getItemId(int i) {
        return filteredExercises.get(i).getId();
    }

    @Override
    public int getItemCount() {
        return filteredExercises.size();
    }

    public Filter getFilter() {
        if (filter == null) {
            filter = new ExerciseFilter();
        }
        return filter;
    }

    private class ExerciseFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterPattern = constraint != null ? constraint.toString().toLowerCase().trim() : "";

            FilterResults results = new FilterResults();
            List<Exercise> filteredList = new ArrayList<>();

            if (filterPattern.isEmpty()) {
                // If no filter is applied, return the original exercises
                filteredList.addAll(List.of(exercises));
            } else {
                // Filter by name and category
                for (Exercise exercise : exercises) {
                    if (exercise.getName().toLowerCase().contains(filterPattern) ||
                            exercise.getExerciseCategory().toString().toLowerCase().contains(filterPattern)) {
                        filteredList.add(exercise);
                    }
                }
            }

            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredExercises.clear();
            filteredExercises.addAll((List<Exercise>) results.values);
            notifyDataSetChanged(); // Notify adapter about data change
        }
    }
}
