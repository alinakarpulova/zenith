package com.example.zenith.models;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Workout {
    private String name;
    private String note;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private ArrayList<WorkoutExercise> workoutExerciseList;

    public Workout(String name) {
        this.name = name;
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

    private boolean containsExercise(Exercise exercise) {
        for (WorkoutExercise workoutExercise : workoutExerciseList) {
            if (workoutExercise.getExercise().getId() == exercise.getId()) {
                return true;
            }
        }
        return false;
    }
}
