package com.example.zenith.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.zenith.models.Exercise;
import com.example.zenith.models.ExerciseBodyPart;
import com.example.zenith.models.ExerciseCategory;
import com.example.zenith.models.ExerciseSet;
import com.example.zenith.models.Workout;
import com.example.zenith.models.WorkoutExercise;

import org.threeten.bp.LocalDateTime;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String ASSETS_PATH = "databases";
    private static final String DATABASE_NAME = "zenith.db";
    private static final int DATABASE_VERSION = 1;

    private final Context context;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    // Copy the prepopulated database from assets to internal storage if it doesn't exist
    public void copyDatabaseIfNeeded() throws IOException {
        File dbFile = context.getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists()) {
            dbFile.getParentFile().mkdirs(); // Ensure the parent directories exist
            try (InputStream input = context.getAssets().open(ASSETS_PATH + "/" + DATABASE_NAME);
                 FileOutputStream output = new FileOutputStream(dbFile)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = input.read(buffer)) > 0) {
                    output.write(buffer, 0, length);
                }
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Ignored since database is preloaded
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public Exercise getExercise(int id) {
        String query = "SELECT * FROM exercises WHERE ID=?";
        SQLiteDatabase db = getReadableDatabase();
        String[] selectionArgs = new String[]{String.valueOf(id)};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        Exercise exercise = null;
        if (cursor.moveToFirst()) {
            int fetched_id = cursor.getInt(0);
            String name = cursor.getString(1);
            String image = cursor.getString(2);
            String instructions = cursor.getString(3);
            ExerciseCategory category = ExerciseCategory.fromString(cursor.getString(4));
            ExerciseBodyPart bodyPart = ExerciseBodyPart.fromString(cursor.getString(5));
            boolean deletable = cursor.getInt(6) == 1;
            exercise = new Exercise(fetched_id, name, image, instructions, bodyPart, category, deletable);
        }
        cursor.close();
        db.close();

        return exercise;
    }


    public List<Exercise> getExerciseList() {
        List<Exercise> exercises = new ArrayList<>();

        String query = "SELECT * FROM exercises WHERE ID < 100";
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String image = cursor.getString(2);
                String instructions = cursor.getString(3);
                ExerciseCategory category = ExerciseCategory.fromString(cursor.getString(4));
                ExerciseBodyPart bodyPart = ExerciseBodyPart.fromString(cursor.getString(5));
                boolean deletable = cursor.getInt(6) == 1;
                exercises.add(new Exercise(id, name, image, instructions, bodyPart, category, deletable));
            } while (cursor.moveToNext());
        }
        // Close connections
        cursor.close();
        db.close();
        return exercises;
    }

    public List<WorkoutExercise> getWorkoutExercises(int workoutId) {
        List<WorkoutExercise> workoutExercises = new ArrayList<>();
        String query = "SELECT * FROM workout_exercises where workoutId = ?";
        SQLiteDatabase db = getReadableDatabase();
        String[] selectionArgs = new String[]{String.valueOf(workoutId)};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseDefs.ID));
                int exerciseId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseDefs.WORKOUT_EXERCISE_EXERCISE));
                Exercise exercise = getExercise(exerciseId);
                WorkoutExercise workoutExercise = new WorkoutExercise(id, exercise);
                workoutExercise.setExerciseSets(getExerciseSets(id));
                workoutExercises.add(workoutExercise);
            } while (cursor.moveToNext());
        }
        return workoutExercises;
    }

    public Workout getWorkout(int workoutId) {
        String query = "SELECT * FROM workouts WHERE ID=?";
        SQLiteDatabase db = getReadableDatabase();
        String[] selectionArgs = new String[]{String.valueOf(workoutId)};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        Workout workout = null;
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String workoutName = cursor.getString(1);
            LocalDateTime startTime = LocalDateTime.parse(cursor.getString(2));
            LocalDateTime endTime = LocalDateTime.parse(cursor.getString(3));
            workout = new Workout(id, workoutName, startTime, endTime);

            // Get workouts exercises
            workout.setWorkoutExerciseList(new ArrayList<>(getWorkoutExercises(id)));
        }
        return workout;
    }

    public List<Workout> getWorkoutList() {
        List<Workout> workouts = new ArrayList<>();

        String query = "SELECT w.id as workoutId, w.name as workoutName, w.startTime, w.endTime,\n" +
                "   we.id as workoutExerciseId,\n" +
                "   e.id as exerciseId, e.name as exerciseName, e.image, e.instructions, e.exerciseCategory, e.exerciseBodyPart,\n" +
                "   wes.id as setId, wes.weight, wes.repetitions, wes.completed\n" +
                "   FROM workouts w\n" +
                "   LEFT JOIN workout_exercises we ON w.id = we.workout_id\n" +
                "   LEFT JOIN exercises e ON we.exercise_id = e.id\n" +
                "   LEFT JOIN workout_exercise_sets wes ON we.id = wes.workout_exercise_id\n" +
                "   ORDER BY w.endTime DESC;";


        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Map<Integer, Workout> workoutMap = new HashMap<>();
        Map<Integer, WorkoutExercise> workoutExerciseMap = new HashMap<>();

        if (cursor.moveToFirst()) {
            do {
                // Extract workout data
                int workoutId = cursor.getInt(cursor.getColumnIndexOrThrow("workoutId"));
                String workoutName = cursor.getString(cursor.getColumnIndexOrThrow("workoutName"));
                LocalDateTime startTime = LocalDateTime.parse(cursor.getString(cursor.getColumnIndexOrThrow("startTime")));
                LocalDateTime endTime = LocalDateTime.parse(cursor.getString(cursor.getColumnIndexOrThrow("endTime")));

                Workout workout = workoutMap.getOrDefault(workoutId, new Workout(workoutId, workoutName, startTime, endTime));

                // Extract workout exercise data
                int workoutExerciseId = cursor.getInt(cursor.getColumnIndexOrThrow("workoutExerciseId"));

                // Only create a new WorkoutExercise if it's not already in the workout
                WorkoutExercise workoutExercise = workoutExerciseMap.get(workoutExerciseId);

                if (workoutExerciseId > 0 && workoutExercise == null) {
                    // Extract exercise data
                    int exerciseId = cursor.getInt(cursor.getColumnIndexOrThrow("exerciseId"));
                    String exerciseName = cursor.getString(cursor.getColumnIndexOrThrow("exerciseName"));
                    System.out.println(exerciseName);
                    // Convert string values to enums
                    String exerciseCategoryStr = cursor.getString(cursor.getColumnIndexOrThrow("exerciseCategory"));
                    ExerciseCategory exerciseCategory = ExerciseCategory.fromString(exerciseCategoryStr);

                    String exerciseBodyPartStr = cursor.getString(cursor.getColumnIndexOrThrow("exerciseBodyPart"));
                    ExerciseBodyPart exerciseBodyPart = ExerciseBodyPart.fromString(exerciseBodyPartStr);

                    Exercise exercise = new Exercise(exerciseId, exerciseName, exerciseCategory, exerciseBodyPart);

                    workoutExercise = new WorkoutExercise(workoutExerciseId, exercise);
                    workoutExerciseMap.put(workoutExerciseId, workoutExercise);
                    workout.addWorkoutExercise(workoutExercise);
                }

                // Extract set data
                int setId = cursor.getInt(cursor.getColumnIndexOrThrow("setId"));
                if (setId > 0) {
                    float weight = cursor.getFloat(cursor.getColumnIndexOrThrow("weight"));
                    int repetitions = cursor.getInt(cursor.getColumnIndexOrThrow("repetitions"));
                    ExerciseSet set = new ExerciseSet(setId, repetitions, weight);
                    workoutExercise.addSet(set);
                }

                workoutMap.put(workoutId, workout);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        workouts.addAll(workoutMap.values());
        return workouts;
    }

    public List<ExerciseSet> getExerciseSets(int workoutExerciseId) {
        List<ExerciseSet> exerciseSets = new ArrayList<>();
        String query = "SELECT * FROM exercise_sets WHERE ID=?";
        SQLiteDatabase db = getReadableDatabase();
        String[] selectionArgs = new String[]{String.valueOf(workoutExerciseId)};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseDefs.ID));
                int reps = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseDefs.WORKOUT_EXERCISE_SET_REPS));
                float weight = cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseDefs.WORKOUT_EXERCISE_SET_WEIGHT));
                exerciseSets.add(new ExerciseSet(id, reps, weight));
            } while (cursor.moveToNext());
        }
        return exerciseSets;
    }

    public void saveWorkout(Workout workout) {
        SQLiteDatabase db = getWritableDatabase();

        List<WorkoutExercise> workoutExercises = workout.getWorkoutExerciseList();
        // Start transaction, only save the records if no errors occur, otherwise roll back
        db.beginTransaction();
        try {
            // Save workout
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseDefs.WORKOUT_NAME, workout.getName());
            contentValues.put(DatabaseDefs.WORKOUT_START, workout.getStartTime().toString());
            contentValues.put(DatabaseDefs.WORKOUT_END, LocalDateTime.now().toString());
            int workout_id = (int) db.insert(DatabaseDefs.WORKOUT_TABLE, null, contentValues);
            // Save workout exercises

            for (WorkoutExercise workoutExercise : workoutExercises) {
                contentValues = new ContentValues();
                contentValues.put(DatabaseDefs.WORKOUT_EXERCISE_EXERCISE, workoutExercise.getExercise().getId());
                contentValues.put(DatabaseDefs.WORKOUT_EXERCISE_WORKOUT, workout_id);
                int workout_exercise_id = (int) db.insert(DatabaseDefs.WORKOUT_EXERCISE_TABLE, null, contentValues);

                for (ExerciseSet exerciseSet : workoutExercise.getExerciseSets()) {
                    contentValues = new ContentValues();
                    contentValues.put(DatabaseDefs.WORKOUT_EXERCISE_SET_REPS, exerciseSet.getRepetitions());
                    contentValues.put(DatabaseDefs.WORKOUT_EXERCISE_SET_WEIGHT, exerciseSet.getWeight());
                    contentValues.put(DatabaseDefs.WORKOUT_EXERCISE_SET_COMPLETED, exerciseSet.isCompleted());
                    contentValues.put(DatabaseDefs.WORKOUT_EXERCISE_SET_WORKOUT_EXERCISE, workout_exercise_id);
                    db.insert(DatabaseDefs.WORKOUT_EXERCISE_SET_TABLE, null, contentValues);
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    public void addExercise(Exercise exercise) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseDefs.EXERCISE_NAME, exercise.getName());
        contentValues.put(DatabaseDefs.EXERCISE_CATEGORY, exercise.getExerciseCategory().toString());
        contentValues.put(DatabaseDefs.EXERCISE_BODYPART, exercise.getExerciseBodyPart().toString());
        contentValues.put(DatabaseDefs.EXERCISE_MODIFIABLE, exercise.isModifiable());
        db.insert(DatabaseDefs.EXERCISE_TABLE, null, contentValues);
    }

}
