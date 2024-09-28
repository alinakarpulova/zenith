package com.example.zenith.models;

public class Exercise {
    private int id;
    private String name;
    private String description;
    private ExerciseCategory exerciseCategory;
    private ExerciseBodyPart exerciseBodyPart;
    private boolean deletable;


    public Exercise(int id, String name, String description, ExerciseBodyPart exerciseBodyPart, ExerciseCategory exerciseCategory, boolean deletable) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.exerciseBodyPart = exerciseBodyPart;
        this.exerciseCategory = exerciseCategory;
        this.deletable = deletable;
    }

    public ExerciseBodyPart getExerciseBodyPart() {
        return exerciseBodyPart;
    }

    public int getId(){
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
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
