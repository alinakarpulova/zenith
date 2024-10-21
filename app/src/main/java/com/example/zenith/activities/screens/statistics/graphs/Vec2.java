package com.example.zenith.activities.screens.statistics.graphs;

public class Vec2 {
    public int x;
    public int y;

    public Vec2() {
        this.x = 0;
        this.y = 0;
    }

    public Vec2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Vec2 zero() {
        return new Vec2();
    }

    public static Vec2 multiply(Vec2 a, Vec2 b) {
        return new Vec2(a.x * b.x, a.y * b.y);
    }

    public static Vec2 subtract(Vec2 a, Vec2 b) {
        return new Vec2(a.x - b.x, a.y - b.y);
    }

    public static Vec2 multiply(Vec2 a, int constant) {
        return new Vec2(a.x * constant, a.y * constant);
    }

    @Override
    public String toString() {
        return "{x: " + x + ", y: " + y + "}";
    }

}
