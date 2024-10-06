package com.example.zenith.activities.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zenith.R;
import com.example.zenith.models.Exercise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ExerciseDialogRowAdapter extends RecyclerView.Adapter<ExerciseDialogRowAdapter.ViewHolder> {
    private Exercise[] exercises;
    private Filter filter;
    private List<Exercise> filteredExercises;
    private HashMap<Integer, Boolean> checkedStates; // Track checked state for each item


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView category;
        private final CheckBox checkBox;

        public ViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.checked_exercise_name);
            category = view.findViewById(R.id.checked_exercise_category);
            checkBox = view.findViewById(R.id.checked_exercise_checkbox);
        }

        public TextView getName() {
            return name;
        }

        public TextView getCategory() {
            return category;
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }
    }

    public ExerciseDialogRowAdapter(Exercise[] exercises) {
        this.exercises = exercises;
        this.filteredExercises = new ArrayList<>(Arrays.asList(exercises));
        this.checkedStates = new HashMap<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.checked_item_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getName().setText(filteredExercises.get(position).getName());
        viewHolder.getCategory().setText(filteredExercises.get(position).getExerciseCategory().toString());

        // Set the checked state based on the tracked value
        viewHolder.getCheckBox().setChecked(checkedStates.getOrDefault(position, false));

        // Handle the checkbox click event
        viewHolder.getCheckBox().setOnCheckedChangeListener((buttonView, isChecked) -> {
            checkedStates.put(position, isChecked); // Update the checked state
        });

        // Handle item click to toggle the checkbox
        viewHolder.itemView.setOnClickListener(v -> {
            boolean isChecked = !checkedStates.getOrDefault(position, false);
            checkedStates.put(position, isChecked); // Update the checked state
            viewHolder.getCheckBox().setChecked(isChecked); // Set the checkbox state
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
            filter = new ExerciseDialogRowAdapter.ExerciseFilter();
        }
        return filter;
    }


    public void setItemChecked(Exercise itemChecked, Boolean checked) {
        int index = filteredExercises.indexOf(itemChecked);
        if (index != -1) {
            checkedStates.put(index, checked);
            notifyItemChanged(index); // Notify the adapter of the change
        }
    }

    public List<Exercise> getCheckedExercises() {
        List<Exercise> checkedExercises = new ArrayList<>();
        for (int i = 0; i < filteredExercises.size(); i++) {
            if (checkedStates.getOrDefault(i, false)) {
                checkedExercises.add(filteredExercises.get(i));
            }
        }
        return checkedExercises;
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
