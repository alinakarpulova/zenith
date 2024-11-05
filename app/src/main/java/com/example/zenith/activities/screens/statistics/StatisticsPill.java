package com.example.zenith.activities.screens.statistics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.example.zenith.R;
import com.example.zenith.models.WorkoutStatistic;

@SuppressLint("ViewConstructor")
public class StatisticsPill extends ConstraintLayout {
    private WorkoutStatistic workoutStatistic;
    private View view;

    public StatisticsPill(Context context, WorkoutStatistic statistic) {
        super(context);
        this.workoutStatistic = statistic;
        init(context);
    }

    private void init(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.statistics_pill, this, true);
        view.setPadding(getValueInDP(context, 4), getValueInDP(context, 8),
                getValueInDP(context, 4), getValueInDP(context, 8));


        TextView heading = view.findViewById(R.id.statistics_title);
        TextView data = view.findViewById(R.id.statistics_data);

        heading.setText(workoutStatistic.getName());
        data.setText(workoutStatistic.getData().toString());
    }

    public static int getValueInDP(Context context, int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }

}
