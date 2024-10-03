package com.example.zenith.controllers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.zenith.models.Exercise;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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

}
