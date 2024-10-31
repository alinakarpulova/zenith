package com.example.zenith.controllers;

public class DatabaseDefs {
    public static final String ID = "id";

    public static final String EXERCISE_TABLE = "exercises";
    public static final String EXERCISE_NAME = "name";
    public static final String EXERCISE_INSTRUCTIONS = "instructions";
    public static final String EXERCISE_IMAGE = "image";
    public static final String EXERCISE_CATEGORY = "exerciseCategory";
    public static final String EXERCISE_BODYPART = "exerciseBodypart";
    public static final String EXERCISE_MODIFIABLE = "deletable";

    public static final String WORKOUT_TABLE = "workouts";
    public static final String WORKOUT_NAME = "name";
    public static final String WORKOUT_START = "startTime";
    public static final String WORKOUT_END = "endTime";

    public static final String WORKOUT_EXERCISE_TABLE = "workout_exercises";
    public static final String WORKOUT_EXERCISE_WORKOUT = "workout_id";
    public static final String WORKOUT_EXERCISE_EXERCISE = "exercise_id";

    public static final String WORKOUT_EXERCISE_SET_TABLE = "workout_exercise_sets";
    public static final String WORKOUT_EXERCISE_SET_WEIGHT = "weight";
    public static final String WORKOUT_EXERCISE_SET_REPS = "repetitions";
    public static final String WORKOUT_EXERCISE_SET_COMPLETED = "completed";
    public static final String WORKOUT_EXERCISE_SET_WORKOUT_EXERCISE = "workout_exercise_id";

    public static final String AVG_WEIGHT = "avg_weight";
    public static final String MAX_WEIGHT = "max_weight";
    public static final String MAX_REPS = "max_reps";
    public static final String TOTAL_WEIGHT = "total_weight";
}
