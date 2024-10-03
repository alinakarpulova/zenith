package com.example.zenith.models;

import androidx.annotation.Nullable;

public class Exercise {
    private int id;
    private String name;
    private String image;
    private String instructions;
    private ExerciseCategory exerciseCategory;
    private ExerciseBodyPart exerciseBodyPart;
    private boolean deletable;


    public Exercise(String name, ExerciseCategory category, ExerciseBodyPart bodyPart) {
        this.name = name;
        this.exerciseCategory = category;
        this.exerciseBodyPart = bodyPart;
    }


    public Exercise(@Nullable Integer id, String name, String image,String description, ExerciseBodyPart exerciseBodyPart, ExerciseCategory exerciseCategory, boolean deletable) {
        if (id != null) {
            this.id = id;
        }
        this.image = image;
        this.name = name;
        this.instructions = description;
        this.exerciseBodyPart = exerciseBodyPart;
        this.exerciseCategory = exerciseCategory;
        this.deletable = deletable;
    }

    public ExerciseBodyPart getExerciseBodyPart() {
        return exerciseBodyPart;
    }

    public int getId() {
        return id;
    }

    public void setExerciseBodyPart(ExerciseBodyPart exerciseBodyPart) {
        this.exerciseBodyPart = exerciseBodyPart;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return instructions;
    }

    public void setDescription(String description) {
        this.instructions = description;
    }

    public ExerciseCategory getExerciseCategory() {
        return exerciseCategory;
    }

    public void setExerciseCategory(ExerciseCategory exerciseCategory) {
        this.exerciseCategory = exerciseCategory;
    }

    public boolean isDeletable() {
        return deletable;
    }

}
