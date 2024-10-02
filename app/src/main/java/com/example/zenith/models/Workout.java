package com.example.zenith.models;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.threeten.bp.LocalDateTime;


public class Workout {
    private String name;
    private String note;
    private final LocalDateTime startTime;
    private LocalDateTime endTime;

    private ArrayList<WorkoutExercise> workoutExerciseList;

    public Workout(String name) {
        this.name = name;
        this.startTime = LocalDateTime.now();
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
