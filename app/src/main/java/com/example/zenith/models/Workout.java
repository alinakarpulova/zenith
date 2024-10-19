package com.example.zenith.models;

import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;
import java.util.List;


public class Workout {
    private int id;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private ArrayList<WorkoutExercise> workoutExerciseList = new ArrayList<>();

    public Workout(int id, String name, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }

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

    public void setWorkoutExerciseList(ArrayList<WorkoutExercise> workoutExercises){
        workoutExerciseList = workoutExercises;
    }

    public void addWorkoutExercise(WorkoutExercise workoutExercise) {
        if (!containsExercise(workoutExercise.getExercise())) {
            workoutExerciseList.add(workoutExercise);
        }
    }

    public ArrayList<WorkoutExercise> getWorkoutExerciseList() {
        return workoutExerciseList;
    }

    public void removeWorkoutExerciseByIndex(int index) {
        if (index >= 0 && index < workoutExerciseList.size()) {
            workoutExerciseList.remove(index);
        }
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
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
