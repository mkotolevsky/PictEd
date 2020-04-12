package com.mycompany.picteditor;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.json.JSONStringer;

public class PointFigure extends AbstractFigure {

    private Color c;
    private double r;

    public PointFigure(Point position, Color c, double r) {
        super(position);
        this.c = c;
        this.r = r;
    }

    public Color getC() {
        return c;
    }

    public void setC(Color c) {
        this.c = c;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    @Override
    public TypeFigure type() {
        return TypeFigure.POINT;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(c);
        gc.fillOval(position.getX(), position.getY(), r, r);
    }

    @Override
    public String toJSONString() {
        return new JSONStringer()
                .object()
                .key("pos").value(position)
                .key("type").value(type().toString())
                .key("color").value(c.toString())
                .key("radius").value(r)
                .endObject()
                .toString();
    }
}
