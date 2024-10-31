package com.example.zenith.activities.screens.statistics.graphs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;

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
        double maxHeight = Arrays.stream(points)
                .mapToDouble(point -> point.y)
                .max()
                .orElse(0);
        double maxLength = Arrays.stream(points)
                .mapToDouble(point -> point.x)
                .max().orElse(0);
        getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            screenDimensions = new Vec2(getWidth(), getHeight());
            grid = Grid.fromNumCells(new Vec2((float) maxLength + 1, (float) maxHeight + 1), screenDimensions, chartBounds);
            invalidate();
        });

        if (screenDimensions != null){
            grid = Grid.fromNumCells(new Vec2(10f, (float) maxHeight + 1), screenDimensions, chartBounds);
            invalidate();
        }
    }

    public void toggleLabels(){
        labelsEnabled = false;
    }

    public boolean isLabelsEnabled(){
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
        dataPaint.setColor(MaterialColors.getColor(getRootView(), com.google.android.material.R.attr.colorPrimary));
        dataPaint.setStrokeWidth(8);
        dataPaint.setStyle(Paint.Style.FILL);
    }


    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        if (grid != null) {
            drawGrid(canvas, grid);
            drawAxisWithLabels(canvas, grid);
            drawPointsWithLines(canvas, grid);
        }
    }

    private void drawGrid(Canvas canvas, Grid grid) {
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
//        for (int i = 0; i < cells.y; i++) {
//            canvas.drawLine(chartBounds.x, chartBounds.y + i * cellSize.y, gridSize.x, chartBounds.y + i * cellSize.y, gridPaint);
//        }
    }

    private void drawAxisWithLabels(Canvas canvas, Grid grid) {
        Vec2 chartSize = Vec2.subtract(grid.getScreenDimensions(), chartBounds);
        // Draw X axis
        canvas.drawLine(chartBounds.x, chartSize.y, chartSize.x, chartSize.y, axisPaint);
        // Draw Y axis
        canvas.drawLine(chartBounds.x, chartBounds.y, chartBounds.x, chartSize.y, axisPaint);


        if (!isLabelsEnabled()){
            // Skip label drawing
            return;
        }
        System.out.println("Drawing Labels");
        int labelMultiplier = 1;
        if (grid.getNumCells().y > 10 && grid.getNumCells().y < 100){
            labelMultiplier = 10;
        }else if (grid.getNumCells().y > 100 && grid.getNumCells().y < 1000){
            labelMultiplier = 100;
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

}
