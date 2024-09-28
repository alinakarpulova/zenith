package com.example.zenith.models;

import androidx.annotation.NonNull;

public enum ExerciseCategory {
    BARBELL("Barbell"),
    DUMBBELL("Dumbbell"),
    MACHINE("Machine"),
    WEIGHTED_BODY_WEIGHT("Weighted Body Weight"),
    ASSISTED_BODY_WEIGHT("Assisted Body Weight"),
    CARDIO("Cardio"),
    DURATION("Duration");
    private final String value;

    ExerciseCategory(String value) {
        this.value = value;
    }

    @NonNull
    @Override
    public String toString() {
        return value;
    }
}