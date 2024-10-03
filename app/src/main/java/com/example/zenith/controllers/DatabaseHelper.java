package com.example.zenith.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.zenith.models.Exercise;
import com.example.zenith.models.ExerciseBodyPart;
import com.example.zenith.models.ExerciseCategory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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

    public List<Exercise> getExerciseList() {
        List<Exercise> exercises = new ArrayList<>();

        String query = "SELECT * FROM exercises where id < 20 OR deletable = 1";
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
