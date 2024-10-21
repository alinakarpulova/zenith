package com.example.zenith.activities.screens.statistics.graphs;

public class Grid {
    private Vec2 cellSize;
    private Vec2 numCells;
    private Vec2 screenDimensions;

    private Grid(Vec2 cellSize, Vec2 numCells, Vec2 screenDimensions) {
        this.cellSize = cellSize;
        this.numCells = numCells;
        this.screenDimensions = screenDimensions;
    }

    public static Grid fromCellSize(Vec2 cellSize, Vec2 screenDimensions) {
        Vec2 numCells = calcNumCells(cellSize, screenDimensions);
        return new Grid(cellSize, numCells, screenDimensions);
    }

    public static Grid fromNumCells(Vec2 numCells, Vec2 screenDimensions, Vec2 bounds) {

        Vec2 cellSize = calcCellSize(numCells, screenDimensions, bounds);
        System.out.println(cellSize);
        return new Grid(cellSize, numCells, screenDimensions);
    }

    private static Vec2 calcNumCells(Vec2 cellSize, Vec2 screenDimensions) {
        return new Vec2(screenDimensions.x / cellSize.x, screenDimensions.y / cellSize.y);
    }

    private static Vec2 calcCellSize(Vec2 numCells, Vec2 screenDimensions, Vec2 bounds) {
        return new Vec2((screenDimensions.x - (2 * bounds.x)) / numCells.x,
                (screenDimensions.y - (2 * bounds.y)) / numCells.y);
    }

    public Vec2 getCellSize() {
        return this.cellSize;
    }

    public Vec2 getNumCells() {
        return this.numCells;
    }

    public Vec2 getScreenDimensions() {
        return screenDimensions;
    }
}
