package com.example.zenith.models;

import java.util.Collections;
import java.util.List;

public class WorkoutExercise {
    private final Exercise exercise;
    private List<ExerciseSet> exerciseSets = Collections.emptyList();

    public WorkoutExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Exercise getExercise() {
        return exercise;
    }
}
