package com.example.zenith.activities.screens.workouts;

import static androidx.core.util.TypedValueCompat.dpToPx;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.zenith.R;
import com.example.zenith.models.ExerciseSet;
import com.google.android.material.color.MaterialColors;

@SuppressLint("ViewConstructor")
public class WorkoutSetRow extends TableRow {
    private View view;
    private final ExerciseSet exerciseSet;
    private int setIndex;
    private TextView setIndexText;
    private ImageButton completeBtn;
    private ImageButton deleteBtn;

    public WorkoutSetRow(Context context, ExerciseSet exerciseSet, int setIndex, OnSetRemoveListener onRemove) {
        super(context);
        this.exerciseSet = exerciseSet;
        this.setIndex = setIndex;
        init(context, onRemove);
    }

    public void init(Context context, OnSetRemoveListener onSetRemoveListener) {
        view = LayoutInflater.from(context).inflate(R.layout.workout_exercise_set_row, this, true);
        setIndexText = view.findViewById(R.id.set_index);
        setIndexText.setText(String.valueOf(setIndex));

        int paddingLeft = (int) dpToPx(8, Resources.getSystem().getDisplayMetrics());  // 16dp padding on the left and right
        view.setPadding(paddingLeft, view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());


        completeBtn = view.findViewById(R.id.complete_set_btn);
        deleteBtn = view.findViewById(R.id.delete_set_btn);
        updateView();
        completeBtn.setOnClickListener((v) -> {
            exerciseSet.setCompleted(!exerciseSet.isCompleted());
            updateView();
        });

        deleteBtn.setOnClickListener((v) -> {
            onSetRemoveListener.onRemove();
        });
    }

    public void updateView() {
        if (exerciseSet.isCompleted()) {
            view.setBackgroundColor(MaterialColors.getColor(view, com.google.android.material.R.attr.colorPrimary));
            setIndexText.setTextColor(MaterialColors.getColor(view, com.google.android.material.R.attr.colorOnPrimary));
            completeBtn.setColorFilter(MaterialColors.getColor(view, com.google.android.material.R.attr.colorOnPrimary));
            deleteBtn.setColorFilter(MaterialColors.getColor(view, com.google.android.material.R.attr.colorErrorContainer));
        } else {
            view.setBackgroundColor(MaterialColors.getColor(view, com.google.android.material.R.attr.colorSurface));
            setIndexText.setTextColor(MaterialColors.getColor(view, com.google.android.material.R.attr.colorOnSurface));
            completeBtn.setColorFilter(MaterialColors.getColor(view, com.google.android.material.R.attr.colorPrimary));
            deleteBtn.setColorFilter(MaterialColors.getColor(view, com.google.android.material.R.attr.colorError));
        }
    }

    // Interface for remove callback
    public interface OnSetRemoveListener {
        void onRemove();
    }
}