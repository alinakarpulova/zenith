package com.example.zenith.models;

import androidx.annotation.NonNull;

public enum ExerciseBodyPart {
    CORE("Core"),
    ARMS("Arms"),
    BACK("Back"),
    CHEST("Chest"),
    LEGS("Legs"),
    SHOULDERS("Shoulders"),
    FULL_BODY("Full Body"),
    CARDIO("Cardio");

    private final String value;

    ExerciseBodyPart(String value) {
        this.value = value;
    }

    @NonNull
    @Override
    public String toString() {
        return value;
    }

    public static ExerciseBodyPart fromString(String bodyPartString) throws IllegalArgumentException {
        for (ExerciseBodyPart bodyPart : ExerciseBodyPart.values()) {
            if (bodyPart.toString().equals(bodyPartString)) {
                return bodyPart;
            }
        }
        throw new IllegalArgumentException(bodyPartString + " is not a valid Exercise Body Part");
    }
}

