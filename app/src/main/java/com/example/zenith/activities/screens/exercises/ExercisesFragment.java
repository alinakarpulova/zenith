package com.example.zenith.activities.screens.exercises;

import android.app.Dialog;
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
import com.example.zenith.models.Exercise;
import com.example.zenith.models.ExerciseBodyPart;
import com.example.zenith.models.ExerciseCategory;

import java.util.Arrays;
import java.util.List;

public class ExercisesFragment extends Fragment {
    public ExercisesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.exercises_fragment, container, false);
        Button button = view.findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ExerciseNew.class);
                startActivity(intent);
            }
        });
        List<Exercise> exercises = Arrays.asList(
                new Exercise("Bench Press", ExerciseCategory.DUMBBELL, ExerciseBodyPart.CHEST),
                new Exercise("Bench Press", ExerciseCategory.DUMBBELL, ExerciseBodyPart.CHEST),
                new Exercise("Bench Press", ExerciseCategory.DUMBBELL, ExerciseBodyPart.CHEST),
                new Exercise("Bench Press", ExerciseCategory.DUMBBELL, ExerciseBodyPart.CHEST),
                new Exercise("Bench Press", ExerciseCategory.DUMBBELL, ExerciseBodyPart.CHEST),
                new Exercise("Bench Press", ExerciseCategory.DUMBBELL, ExerciseBodyPart.CHEST),
                new Exercise("Bench Press", ExerciseCategory.DUMBBELL, ExerciseBodyPart.CHEST),
                new Exercise("Bench Press", ExerciseCategory.DUMBBELL, ExerciseBodyPart.CHEST),
                new Exercise("Bench Press", ExerciseCategory.DUMBBELL, ExerciseBodyPart.CHEST),
                new Exercise("Bench Press", ExerciseCategory.DUMBBELL, ExerciseBodyPart.CHEST),
                new Exercise("Bench Press", ExerciseCategory.DUMBBELL, ExerciseBodyPart.CHEST),
                new Exercise("Bench Press", ExerciseCategory.DUMBBELL, ExerciseBodyPart.CHEST),
                new Exercise("Bench Press", ExerciseCategory.DUMBBELL, ExerciseBodyPart.CHEST),
                new Exercise("Bench Press", ExerciseCategory.DUMBBELL, ExerciseBodyPart.CHEST),
                new Exercise("Bench Press", ExerciseCategory.DUMBBELL, ExerciseBodyPart.CHEST),
                new Exercise("Bench Press", ExerciseCategory.DUMBBELL, ExerciseBodyPart.CHEST),
                new Exercise("Bench Press", ExerciseCategory.DUMBBELL, ExerciseBodyPart.CHEST),
                new Exercise("Bench Press", ExerciseCategory.DUMBBELL, ExerciseBodyPart.CHEST),
                new Exercise("Bench Press", ExerciseCategory.DUMBBELL, ExerciseBodyPart.CHEST),
                new Exercise("Bench Press", ExerciseCategory.DUMBBELL, ExerciseBodyPart.CHEST)
        );

        ExerciseRowAdapter adapter = new ExerciseRowAdapter((Exercise[]) exercises.toArray());
        RecyclerView recyclerView = view.findViewById(R.id.exercises_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);

        return view;

    }
}
