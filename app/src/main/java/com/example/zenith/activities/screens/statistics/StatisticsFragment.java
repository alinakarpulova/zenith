package com.example.zenith.activities.screens.statistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zenith.R;
import com.example.zenith.activities.adapters.ExerciseRowAdapter;
import com.example.zenith.activities.screens.statistics.graphs.GraphView;
import com.example.zenith.activities.screens.statistics.graphs.Vec2;
import com.example.zenith.components.DialogWithSearchMulti;
import com.example.zenith.controllers.DatabaseHelper;
import com.example.zenith.models.Exercise;
import com.example.zenith.models.WorkoutExercise;
import com.google.android.material.button.MaterialButton;

public class StatisticsFragment extends Fragment {
    private Exercise exercise;
    private MaterialButton selectExerciseBtn;
    private RecyclerView recyclerView;
    private ExerciseRowAdapter adapter;
    private DatabaseHelper dbHelper;

    public StatisticsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.statistics_fragment, container, false);
        selectExerciseBtn = view.findViewById(R.id.exercise_picker_btn);
        dbHelper = new DatabaseHelper(getContext());
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DialogWithSearchMulti exercisesDialog = new DialogWithSearchMulti(dbHelper.getExerciseList(), getContext());

        // Create exercise Dropdown


        GraphView graphView = view.findViewById(R.id.graphView);
        Vec2[] points = new Vec2[]{new Vec2(11, 15), new Vec2(6, 7), new Vec2(10, 11)};
        graphView.setData(points);

        //
    }
}
