package com.example.zenith.models;

import androidx.annotation.NonNull;

public class WorkoutStatistic<T> {
    private String name;
    private T data;

    public WorkoutStatistic(String name, T data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public T getData() {
        return data;
    }

    @NonNull
    @Override
    public String toString(){
        return "WorkoutStatistic: {" + name + ", " + data + "}";
    }
}
