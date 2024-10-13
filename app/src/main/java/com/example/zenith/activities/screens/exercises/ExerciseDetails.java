package com.example.zenith.activities.screens.exercises;

import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.zenith.R;
import com.example.zenith.controllers.DatabaseHelper;
import com.example.zenith.models.Exercise;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLSyntaxErrorException;

public class ExerciseDetails extends AppCompatActivity {
    public static String EXERCISE_ID = "EXERCISE_ID";

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        Bundle instance = getIntent().getExtras();
        int id = instance.getInt(EXERCISE_ID);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        Exercise exercise = databaseHelper.getExercise(id);
        System.out.println(exercise);
        setContentView(R.layout.exercise_details_view);

        Toolbar title = findViewById(R.id.toolbar);
        TextView category = findViewById(R.id.exercise_details_category);
        category.setText(exercise.getExerciseCategory().toString());
        TextView bodypart = findViewById(R.id.exercise_details_bodypart);
        bodypart.setText(exercise.getExerciseBodyPart().toString());
        TextView instructions = findViewById(R.id.exercise_details_instructions);
        instructions.setText(exercise.getInstructions());
        title.setTitle(exercise.getName());

        AssetManager assetManager = getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open("gifs/0001.gif");
            System.out.println("GIF FOUND");
        } catch (IOException e) {
            System.out.println("CANNOT FIND GIF");
            throw new RuntimeException(e);
        }
        Drawable drawable = Drawable.createFromStream(inputStream, null);
        ImageView imageView = findViewById(R.id.exercise_details_img);
        imageView.setImageDrawable(drawable);
    }
}
