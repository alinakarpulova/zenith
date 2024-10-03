package com.example.zenith.activities.screens.exercises;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zenith.R;
import com.example.zenith.activities.adapters.ExerciseRowAdapter;
import com.example.zenith.controllers.DatabaseHelper;
import com.example.zenith.controllers.ExerciseController;
import com.example.zenith.models.Exercise;

public class ExercisesFragment extends Fragment {
    private ExerciseController exerciseController;
    private RecyclerView recyclerView;
    private ExerciseRowAdapter adapter;

    public ExercisesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        exerciseController = new ExerciseController(new DatabaseHelper(getContext()));

        View view = inflater.inflate(R.layout.exercises_fragment, container, false);
        Button button = view.findViewById(R.id.button2);

        recyclerView = view.findViewById(R.id.exercises_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        updateList();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ExerciseNew.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void updateList() {
        adapter = new ExerciseRowAdapter(exerciseController.getExerciseList().toArray(new Exercise[0]));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateList();
    }
}
