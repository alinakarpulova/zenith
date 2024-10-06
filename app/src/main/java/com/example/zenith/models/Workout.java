package com.example.zenith.models;

import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;


public class Workout {
    private String name;
    private String note;
    private final LocalDateTime startTime;
    private LocalDateTime endTime;

    private ArrayList<WorkoutExercise> workoutExerciseList;

    public Workout(String name) {
        this.name = name;
        this.startTime = LocalDateTime.now();
        workoutExerciseList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addWorkoutExercise(WorkoutExercise workoutExercise) {
        if (!containsExercise(workoutExercise.getExercise())) {
            workoutExerciseList.add(workoutExercise);
        }
    }

    public void removeWorkoutExercise(WorkoutExercise workoutExercise) {
        workoutExerciseList.remove(workoutExercise);
    }

    public boolean containsExercise(Exercise exercise) {
        for (WorkoutExercise workoutExercise : workoutExerciseList) {
            if (workoutExercise.getExercise().getId() == exercise.getId()) {
                return true;
            }
        }
        return false;
    }
}
