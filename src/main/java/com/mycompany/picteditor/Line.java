package com.mycompany.picteditor;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.json.JSONStringer;

public class Line extends AbstractFigure {

    private Point start, end;
    private Color c;
    private double lineWeight;

    public Line(Point position, Point start, Point end, Color c, double lineWeight) {
        super(position);
        this.start = start;
        this.end = end;
        this.c = c;
        this.lineWeight = lineWeight;
    }

    public Color getC() {
        return c;
    }

    public void setC(Color c) {
        this.c = c;
    }

    public double getLineWeight() {
        return lineWeight;
    }

    public void setLineWeight(double lineWeight) {
        this.lineWeight = lineWeight;
    }

    @Override
    public TypeFigure type() {
        return TypeFigure.LINE;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setLineWidth(lineWeight);
        gc.setStroke(c);
        Point start, end;
        start = this.start.plus(position);
        end = this.end.plus(position);
        gc.strokeLine(start.getX(), start.getY(), end.getX(), end.getY());
    }

    @Override
    public String toJSONString() {
        return new JSONStringer()
                .object()
                .key("pos").value(position)
                .key("start").value(start)
                .key("end").value(end)
                .key("type").value(type().toString())
                .key("color").value(c.toString())
                .key("lineWeight").value(lineWeight)
                .endObject()
                .toString();
    }
}
