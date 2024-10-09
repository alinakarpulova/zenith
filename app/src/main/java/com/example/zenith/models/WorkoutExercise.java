package com.example.zenith.models;

import java.util.ArrayList;
import java.util.List;

public class WorkoutExercise {
    private final Exercise exercise;
    private final List<ExerciseSet> exerciseSets;

    public WorkoutExercise(Exercise exercise) {
        this.exercise = exercise;
        this.exerciseSets = new ArrayList<>();
        this.exerciseSets.add(new ExerciseSet(0, 0, 0));
    }

    public Exercise getExercise() {
        return exercise;
    }

    public ExerciseSet getExerciseSetByIndex(int index) {
        return exerciseSets.get(index);
    }

    public void removeSetByIndex(int index) {
        if (index >= 0 && index < exerciseSets.size()) {
            exerciseSets.remove(index);
        }
    }

    public List<ExerciseSet> getExerciseSets() {
        return this.exerciseSets;
    }
}
