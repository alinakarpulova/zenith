package com.example.zenith.activities.screens.workouts;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.zenith.R;
import com.google.android.material.button.MaterialButton;

public class WorkoutsFragment extends Fragment {

    public WorkoutsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.workouts_fragment, container, false);

        MaterialButton button = fragmentView.findViewById(R.id.workout_new_btn);
        button.setOnClickListener((view) -> {
            Intent intent = WorkoutNew.makeIntent(getContext());
            startActivity(intent);
        });


        return fragmentView;
    }
}
