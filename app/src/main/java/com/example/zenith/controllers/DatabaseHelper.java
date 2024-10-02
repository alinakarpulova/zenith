package com.example.zenith.controllers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, "workout.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String exercisesTable = "CREATE TABLE IF NOT EXISTS exercises ( " +
                "    id INTEGER PRIMARY KEY,\n" +
                "    name TEXT NOT NULL,\n" +
                "    image TEXT, " +
                "    description TEXT,\n" +
                "    exerciseCategory TEXT NOT NULL,\n" +
                "    exerciseBodyPart TEXT NOT NULL,\n" +
                "    deletable INTEGER NOT NULL DEFAULT 0" +
                ");";

        String workoutExercise = "CREATE TABLE IF NOT EXISTS `workout-exercises` (" +
                "   id INTEGER PRIMARY KEY," +
                "   workout_id INTEGER NOT NULL," +
                "   exercise_id INTEGER NOT NULL, " +
                "   FOREIGN KEY (workout_id) REFERENCES `workouts` (`id`)," +
                "   FOREIGN KEY (exercise_id) REFERENCES `exercises` (`id`)" +
                ");";

        String exerciseSet = "CREATE TABLE IF NOT EXISTS `exercise-sets` (" +
                "   id INTEGER PRIMARY KEY," +
                "   weight DECIMAL(10, 2)," +
                "   repetitions INTENGER," +
                "   exercise_id INTEGER NOT NULL," +
                "   FOREIGN KEY (exercise_id) REFERENCES `workout-exercises` (`id`)" +
                ");";

        String workout = "CREATE TABLE IF NOT EXISTS `workouts` ( " +
                "   id INTEGER PRIMARY KEY," +
                "   name TEXT NOT NULL," +
                "   startTime TIMESTAMP DEFAULT (DATETIME('now'))," +
                "   endTime TIMESTAMP DEFAULT (DATETIME('now'))," +
                "   note TEXT," +
                "   template INTEGER DEFAULT 0" +
                ");";
        System.out.println("DATABASE CREATE CALLED");

        db.execSQL(exercisesTable);
        db.execSQL(workoutExercise);
        db.execSQL(exerciseSet);
        db.execSQL(workout);
        System.out.println("DATABASE CREATE CALLED");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
