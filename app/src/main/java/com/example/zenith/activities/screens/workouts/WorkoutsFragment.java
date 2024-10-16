package com.example.zenith.activities.screens.workouts;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.zenith.R;
import com.example.zenith.activities.adapters.SimpleWorkoutAdapter;
import com.example.zenith.controllers.DatabaseHelper;
import com.example.zenith.models.Workout;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class WorkoutsFragment extends Fragment {

    private ListView workoutHistoryList;
    private DatabaseHelper databaseHelper;

    public WorkoutsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.workouts_fragment, container, false);

        databaseHelper = new DatabaseHelper(getContext());

        workoutHistoryList = fragmentView.findViewById(R.id.workout_history_list);
        MaterialButton button = fragmentView.findViewById(R.id.workout_new_btn);
        button.setOnClickListener((view) -> {
            Intent intent = WorkoutNew.makeIntent(getContext());
            startActivity(intent);
        });

        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateList();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateList();
    }

    private void updateList() {
        ArrayList<Workout> workouts = (ArrayList<Workout>) databaseHelper.getWorkoutList();
        SimpleWorkoutAdapter itemsAdapter = new SimpleWorkoutAdapter(getContext(), workouts);
        workoutHistoryList.setAdapter(itemsAdapter);
    }
}
