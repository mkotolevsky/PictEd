package com.mycompany.picteditor.builder;

import com.mycompany.picteditor.Figure;
import com.mycompany.picteditor.Point;
import com.mycompany.picteditor.Polygon;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class PolygonBuilder {

    private List<Point> points;
    private Color colorBack, colorLine;
    private double lineWeight;
    private Point position;

    public PolygonBuilder() {
        points = new ArrayList<>();
    }

    public PolygonBuilder addPoint(Point p) {
        points.add(p);
        return this;
    }

    public PolygonBuilder setColorBack(Color colorBack) {
        this.colorBack = colorBack;
        return this;
    }

    public PolygonBuilder setColorLine(Color colorLine) {
        this.colorLine = colorLine;
        return this;
    }

    public PolygonBuilder setLineWeight(double lineWeight) {
        this.lineWeight = lineWeight;
        return this;
    }

    public PolygonBuilder setPosition(Point position) {
        this.position = position;
        return this;
    }

    public Figure build() {
        if (points.size() < 3)
            return null;
        else
            return new Polygon(position, points, colorLine, colorBack, lineWeight);
    }
}
