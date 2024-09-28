package com.example.zenith.models;

public class ExerciseSet {
    private int id;
    private int repetitions;
    private float weight;
    private boolean completed;

    public ExerciseSet(int id, int repetitions, float weight) {
        this.id = id;
        this.repetitions = repetitions;
        this.weight = weight;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public int getRepetitions() {
        return this.repetitions;
    }

    void setWeight(float weight) {
        this.weight = weight;
    }
}
