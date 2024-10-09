package com.example.zenith.activities.screens.workouts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageButton;

import com.example.zenith.R;
import com.example.zenith.models.ExerciseSet;

@SuppressLint("ViewConstructor")
public class WorkoutSetRow extends TableRow {
    private View view;
    private final ExerciseSet exerciseSet;
    private int setIndex;
    private TextView setIndexText;

    public WorkoutSetRow(Context context, ExerciseSet exerciseSet, int setIndex, OnSetCompleteListener onComplete, OnSetRemoveListener onRemove) {
        super(context);
        this.exerciseSet = exerciseSet;
        this.setIndex = setIndex;
        init(context, onComplete, onRemove);
    }

    public void init(Context context, OnSetCompleteListener onComplete, OnSetRemoveListener onRemove) {
        view = LayoutInflater.from(context).inflate(R.layout.workout_exercise_set_row, this, true);
        setIndexText = view.findViewById(R.id.set_index);
        setIndexText.setText(String.valueOf(setIndex));

        AppCompatImageButton completeBtn = view.findViewById(R.id.complete_set_btn);
        AppCompatImageButton deleteBtn = view.findViewById(R.id.delete_set_btn);
        updateView();
        completeBtn.setOnClickListener((v) -> {
            onComplete.onComplete(!exerciseSet.isCompleted());
            updateView();
        });

        deleteBtn.setOnClickListener((v) -> {
            onRemove.onRemove();
        });
    }

    public void updateView() {
        System.out.println(exerciseSet.isCompleted());
        if (exerciseSet.isCompleted()) {
            view.setBackgroundColor(com.google.android.material.R.attr.colorAccent);
        } else {
            view.setBackgroundColor(com.google.android.material.R.attr.colorSurface);
        }
    }

    // Interface for completion callback
    public interface OnSetCompleteListener {
        void onComplete(boolean completed);
    }

    // Interface for remove callback
    public interface OnSetRemoveListener {
        void onRemove();
    }
}
