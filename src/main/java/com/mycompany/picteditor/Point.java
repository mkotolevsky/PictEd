package com.mycompany.picteditor;

import org.json.JSONString;
import org.json.JSONStringer;

import java.util.Objects;

public class Point implements JSONString {

    private double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Point plus(Point p) {
        return new Point(x + p.x, y + p.y);
    }

    public Point minus(Point p) {
        return new Point(x - p.x, y - p.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(point.x, x) == 0 &&
                Double.compare(point.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public String toJSONString() {
        return new JSONStringer()
                .object()
                .key("x").value(x)
                .key("y").value(y)
                .endObject()
                .toString();
    }
}
