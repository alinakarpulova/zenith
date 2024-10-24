package com.example.zenith.activities.screens.statistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.zenith.R;
import com.example.zenith.activities.screens.statistics.graphs.GraphView;
import com.example.zenith.activities.screens.statistics.graphs.Vec2;
import com.example.zenith.models.Exercise;

public class StatisticsFragment extends Fragment {
    private Exercise exercise;
    public StatisticsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.statistics_fragment, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GraphView graphView = view.findViewById(R.id.graphView);
        Vec2[] points = new Vec2[]{new Vec2(11, 15), new Vec2(6, 7), new Vec2(10, 11)};
        graphView.setData(points);

        //
    }
}
