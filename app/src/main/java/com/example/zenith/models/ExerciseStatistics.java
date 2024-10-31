package com.example.zenith.models;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ExerciseStatistics {
    private String exerciseName;
    private final List<ExerciseStatisticsData> statisticsData;

    public ExerciseStatistics(String exerciseName) {
        this.exerciseName = exerciseName;
        this.statisticsData = new ArrayList<>();
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String name) {
        this.exerciseName = name;
    }

    public void addStats(ExerciseStatisticsData data) {
        this.statisticsData.add(data);
    }

    public List<ExerciseStatisticsData> getData() {
        return statisticsData;
    }

    public <R> List<R> getStatistic(Function<ExerciseStatisticsData, R> fieldGetter) {
        return statisticsData.stream()
                .map(fieldGetter)
                .collect(Collectors.toList());
    }

}
