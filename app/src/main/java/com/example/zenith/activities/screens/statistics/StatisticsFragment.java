package com.example.zenith.activities.screens.statistics;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.zenith.R;
import com.example.zenith.activities.screens.statistics.graphs.GraphView;
import com.example.zenith.activities.screens.statistics.graphs.Vec2;
import com.example.zenith.components.DialogWithSearchSingle;
import com.example.zenith.controllers.DatabaseHelper;
import com.example.zenith.models.Exercise;
import com.example.zenith.models.ExerciseStatistics;
import com.example.zenith.models.ExerciseStatisticsData;
import com.example.zenith.models.WorkoutStatistic;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class StatisticsFragment extends Fragment {
    private MaterialButton selectExerciseBtn;
    private DatabaseHelper dbHelper;
    private TextView graphTitle;
    private Toolbar toolbar;
    private LinearLayout linearLayout;
    GraphView graphView;


    public StatisticsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.statistics_fragment, container, false);
        selectExerciseBtn = view.findViewById(R.id.exercise_picker_btn);
        graphTitle = view.findViewById(R.id.graphTitle);
        toolbar = view.findViewById(R.id.toolbar);
        linearLayout = view.findViewById(R.id.stats_linear_layout);
        dbHelper = new DatabaseHelper(getContext());
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar.setTitle("Statistics");

        DialogWithSearchSingle<Exercise> dialog = new DialogWithSearchSingle<>(dbHelper.getExerciseList(), getContext());
        dialog.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (dialog.getSelectedItem() != null) {
                    updateGraphStats(dialog.getSelectedItem());
                }
            }
        });

        List<WorkoutStatistic> stats = dbHelper.getWorkoutStatistics();
        for (WorkoutStatistic statistic : stats) {
            StatisticsPill pill = new StatisticsPill(getContext(), statistic);
            linearLayout.addView(pill);
        }

        selectExerciseBtn.setOnClickListener((v) -> {
            dialog.showDialog();
        });

        graphView = view.findViewById(R.id.graphView);
        // Initialize graph
        Vec2[] points = new Vec2[]{};
        graphView.setData(points);

    }

    private void updateGraphStats(Exercise exercise) {
        ExerciseStatistics statistics = dbHelper.getExerciseStats(exercise.getId());
        if (statistics == null) {
            graphTitle.setText(R.string.no_data);
            graphView.setData(new Vec2[]{});
            return;
        }
        graphTitle.setText(statistics.getExerciseName());
        List<Float> averageWeights = statistics.getStatistic(ExerciseStatisticsData::getMaxWeight);
        List<Vec2> points = new ArrayList<>();
        for (int i = 0; i < averageWeights.size(); i++) {
            points.add(new Vec2(i + 1, averageWeights.get(i)));
        }
        graphView.setData(points.toArray(new Vec2[0]));
    }
}

