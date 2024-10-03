package com.example.zenith.controllers;

import com.example.zenith.models.Exercise;

import java.util.List;

public class ExerciseController {
    private final DatabaseHelper databaseHelper;

    public ExerciseController(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public List<Exercise> getExerciseList() {
        return this.databaseHelper.getExerciseList();
    }

    public void addExercise(Exercise exercise) {
        databaseHelper.addExercise(exercise);
        // Add exercise to database
        // send event to view to refresh list
    }

    public void editExercise(int exerciseId, Exercise exercise) {
        if (exercise.isModifiable()) {

        }
    }


    public void deleteExercise(Exercise exercise) {
        if (exercise.isModifiable()) {

        }
        // Remove exercise from database
        // Send event to view to refresh exercise list

    }
}
