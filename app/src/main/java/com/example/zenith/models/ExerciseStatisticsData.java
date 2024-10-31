package com.example.zenith.models;

public class ExerciseStatisticsData {
    private float averageWeight;
    private float maxWeight;
    private float maxReps;
    private float totalWeight;

    public ExerciseStatisticsData(float averageWeight, float maxWeight, float maxReps, float totalWeight) {
        this.averageWeight = averageWeight;
        this.maxWeight = maxWeight;
        this.maxReps = maxReps;
        this.totalWeight = totalWeight;
    }

    public float getAverageWeight() {
        return averageWeight;
    }

    public float getMaxReps() {
        return maxReps;
    }

    public float getMaxWeight() {
        return maxWeight;
    }

}
