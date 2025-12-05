package com.shaharyi.snake;

public class Point {
    public int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Point))
            return false;
        Point other = (Point) o;
        return this.x == other.x && this.y == other.y;
    }

    public int hashCode() {
        return x * 31 + y;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }
}

