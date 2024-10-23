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

public class GraphView extends View {
    private String title;
    private final Vec2 dimensions = new Vec2();
    private final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
    private final Vec2 chartBounds = new Vec2(dpToPx(30), dpToPx(30));
    private String xLabel;
    private String yLabel;

    private Paint gridPaint;
    private Paint axisPaint;
    private Paint dataPaint;
    private TextPaint axisTextPaint;

    private Vec2[] points = new Vec2[]{new Vec2(1, 1), new Vec2(1.5f, 3.5f), new Vec2(2.5f, 3), new Vec2(3, 4), new Vec2(5, 6)};

    private Grid grid;

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

        Vec2 screenDimensions = new Vec2(getWidth(), getHeight());
        grid = Grid.fromNumCells(new Vec2(10, 10), screenDimensions, chartBounds);
        drawGrid(canvas, grid);
        drawAxisWithLabels(canvas, grid);
        drawPointsWithLines(canvas, grid);
    }


    private void drawGrid(Canvas canvas, Grid grid) {
        Vec2 cells = grid.getNumCells();
        Vec2 cellSize = grid.getCellSize();
        Vec2 gridSize = Vec2.subtract(grid.getScreenDimensions(), chartBounds);
        // Vertical grid lines
        for (int i = 0; i <= cells.x; i++) {
            canvas.drawLine(chartBounds.x + i * cellSize.x, chartBounds.y, chartBounds.x + i * cellSize.x, gridSize.y, gridPaint);
        }
        // Horizontal grid lines
        for (int i = 0; i < cells.y; i++) {
            canvas.drawLine(chartBounds.x, chartBounds.y + i * cellSize.y, gridSize.x, chartBounds.y + i * cellSize.y, gridPaint);
        }
    }

    private void drawAxisWithLabels(Canvas canvas, Grid grid) {
        Vec2 chartSize = Vec2.subtract(grid.getScreenDimensions(), chartBounds);
        // Draw X axis
        canvas.drawLine(chartBounds.x, chartSize.y, chartSize.x, chartSize.y, axisPaint);
        // Draw Y axis
        canvas.drawLine(chartBounds.x, chartBounds.y, chartBounds.x, chartSize.y, axisPaint);

        // Draw Vertical Labels
        for (int i = 1; i < grid.getNumCells().y; i += 1) {
            canvas.drawText(String.valueOf(i), 0, (chartSize.y - (i * grid.getCellSize().y)), axisTextPaint);
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
