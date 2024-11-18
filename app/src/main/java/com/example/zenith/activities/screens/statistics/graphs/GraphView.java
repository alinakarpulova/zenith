package com.example.zenith.activities.screens.statistics.graphs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Debug;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.zenith.R;
import com.google.android.material.color.MaterialColors;

import java.util.Arrays;

public class GraphView extends View {
    private final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
    private final Vec2 chartBounds = new Vec2(dpToPx(30), dpToPx(30));
    private String[] xLabels;
    private String[] yLabels;

    private Paint gridPaint;
    private Paint axisPaint;
    private Paint dataPaint;
    private TextPaint axisTextPaint;


    private Vec2 screenDimensions;
    private Vec2[] points;
    private final Vec2[] range = new Vec2[]{new Vec2(0, 0), new Vec2(0, 0)};
    private Grid grid;

    private boolean gridEnabled = true;
    private boolean labelsEnabled = true;

    public GraphView(Context context) {
        super(context);
        initBrushes();
    }

    public GraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initBrushes();
    }

    public GraphView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBrushes();
    }

    public void setData(Vec2[] dataPoints) {
        this.points = dataPoints;
        findRange(points);
        getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            screenDimensions = new Vec2(getWidth(), getHeight());
            grid = Grid.fromNumCells(new Vec2(range[1].x + 1, range[1].y + 1), screenDimensions, chartBounds);
            invalidate();
        });

        if (screenDimensions != null) {
            grid = Grid.fromNumCells(new Vec2(10f, range[1].y + 1), screenDimensions, chartBounds);
            invalidate();
        }
    }

    public void toggleLabels() {
        labelsEnabled = false;
    }

    public boolean isLabelsEnabled() {
        return labelsEnabled;
    }

    public void toggleGrid() {
        this.gridEnabled = false;
    }

    public boolean isGridEnabled() {
        return this.gridEnabled;
    }

    public void setLabels(String[] xLabels, String[] yLabels) {
        this.xLabels = xLabels;
        this.yLabels = yLabels;
        invalidate();
    }

    private void initBrushes() {

        axisTextPaint = new TextPaint();
        axisTextPaint.setAntiAlias(true);
        axisTextPaint.setTextSize(16 * displayMetrics.density);
        axisTextPaint.setColor(MaterialColors.getColor(getRootView(), com.google.android.material.R.attr.colorOnSurface));

        gridPaint = new Paint();
        gridPaint.setColor(MaterialColors.getColor(getRootView(), com.google.android.material.R.attr.colorOnSurface));
        gridPaint.setStrokeWidth(1);

        axisPaint = new Paint();
        axisPaint.setColor(MaterialColors.getColor(getRootView(), com.google.android.material.R.attr.colorOnSurface));
        axisPaint.setStrokeWidth(8);

        dataPaint = new Paint();
        dataPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        dataPaint.setStrokeWidth(8);
        dataPaint.setStyle(Paint.Style.FILL);
    }


    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        if (grid != null) {
            int diff = (int) (range[1].y);
            int labelMultiplier = 1;
            if (diff > 10 && diff <= 140) {
                labelMultiplier = 10;
            } else if (diff > 140 && diff < 1000) {
                labelMultiplier = 100;
            }
            drawGrid(canvas, grid, labelMultiplier);
            drawAxisWithLabels(canvas, grid, labelMultiplier);
            drawPointsWithLines(canvas, grid);
        }
    }

    private void drawGrid(Canvas canvas, Grid grid, int labelMultiplier) {
        if (!isGridEnabled()) {
            // skip grid drawing
            return;
        }

        Vec2 cells = grid.getNumCells();
        Vec2 cellSize = grid.getCellSize();
        Vec2 gridSize = Vec2.subtract(grid.getScreenDimensions(), chartBounds);
        // Vertical grid lines
        for (int i = 0; i <= cells.x; i++) {
            canvas.drawLine(chartBounds.x + i * cellSize.x, chartBounds.y, chartBounds.x + i * cellSize.x, gridSize.y, gridPaint);
        }

        // Horizontal grid lines
        Log.d("GRID", String.valueOf(cells.y / labelMultiplier));
        for (int i = 0; i <= cells.y / labelMultiplier; i++) {
            canvas.drawLine(chartBounds.x,  gridSize.y - (i * cellSize.y * labelMultiplier), gridSize.x, gridSize.y - (i * cellSize.y * labelMultiplier), gridPaint);
        }
    }

    private void drawAxisWithLabels(Canvas canvas, Grid grid, int labelMultiplier) {
        Vec2 chartSize = Vec2.subtract(grid.getScreenDimensions(), chartBounds);
        // Draw X axis
        canvas.drawLine(chartBounds.x, chartSize.y, chartSize.x, chartSize.y, axisPaint);
        // Draw Y axis
        canvas.drawLine(chartBounds.x, chartBounds.y, chartBounds.x, chartSize.y, axisPaint);


        if (!isLabelsEnabled()) {
            // Skip label drawing
            return;
        }
        // Draw Vertical Labels
        for (int i = 1; i < (grid.getNumCells().y / labelMultiplier); i += 1) {
            canvas.drawText(String.valueOf(i * labelMultiplier), 0, (chartSize.y - (i * grid.getCellSize().y * labelMultiplier)), axisTextPaint);
        }

        // Draw Horizontal Labels
        for (int i = 1; i < grid.getNumCells().x; i += 1) {
            canvas.drawText(String.valueOf(i), i * grid.getCellSize().x + chartBounds.x, grid.getScreenDimensions().y, axisTextPaint);
        }
    }

    private void drawPointsWithLines(Canvas canvas, Grid grid) {
        Vec2 prevPoint = null;
        for (Vec2 point : points) {

            Vec2 currentPoint = pointToGridCoord(point, grid);
            if (prevPoint != null) {
                // Draw line from previous point to current point
                canvas.drawLine(prevPoint.x, prevPoint.y, currentPoint.x, currentPoint.y, dataPaint);
            }
            canvas.drawCircle(currentPoint.x, currentPoint.y, 12, dataPaint);
            prevPoint = currentPoint;
        }
    }

    private Vec2 pointToGridCoord(Vec2 point, Grid grid) {
        // Translate point to grid sized coords
        Vec2 pointCoordsCellSize = Vec2.multiply(point, grid.getCellSize());
        // Get grid offsets with width and height;
        Vec2 gridSize = Vec2.subtract(grid.getScreenDimensions(), chartBounds);
        return new Vec2(chartBounds.x + pointCoordsCellSize.x, gridSize.y - pointCoordsCellSize.y);
    }

    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
    }


    private void findRange(Vec2[] points) {
        double maxHeight = Arrays.stream(points)
                .mapToDouble(point -> point.y)
                .max()
                .orElse(0);

        if (maxHeight == 0){
            maxHeight = 10;
        }

        double maxWidth = Arrays.stream(points)
                .mapToDouble(point -> point.x)
                .max().orElse(0);
        if (maxWidth == 0){
            maxWidth = 10;
        }

        double minHeight = Arrays.stream(points)
                .mapToDouble(point -> point.y)
                .min()
                .orElse(0);
        if (points.length == 1) {
            minHeight = 0;
        }

        double minWidth = Arrays.stream(points)
                .mapToDouble(point -> point.x)
                .min().orElse(0);
        if (points.length == 1) {
            minWidth = 0;
        }

        range[0].x = (float) minWidth;
        range[1].x = (float) maxWidth;
        range[0].y = (float) minHeight;
        range[1].y = (float) maxHeight;
    }


}
