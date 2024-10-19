package com.example.zenith.models;

public class ExerciseSet {
    private int id;
    private int repetitions;
    private float weight;
    private boolean completed;

    public ExerciseSet(int repetitions, float weight) {
        this.repetitions = repetitions;
        this.weight = weight;
    }

    public ExerciseSet(int id, int repetitions, float weight) {
        this.id = id;
        this.repetitions = repetitions;
        this.weight = weight;
        completed = false;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public int getRepetitions() {
        return this.repetitions;
    }

    public float getWeight(){
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }


    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
