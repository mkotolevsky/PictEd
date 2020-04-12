package com.mycompany.picteditor;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.json.JSONStringer;

public class Rectangle extends AbstractFigure {

    private double w, h;
    private Color colorLine, colorBack;
    private double lineWeight;

    public Rectangle(Point position, double w, double h, Color colorLine, Color colorBack, double lineWeight) {
        super(position);
        this.w = w;
        this.h = h;
        this.colorLine = colorLine;
        this.colorBack = colorBack;
        this.lineWeight = lineWeight;
    }

    public Color getColorLine() {
        return colorLine;
    }

    public void setColorLine(Color colorLine) {
        this.colorLine = colorLine;
    }

    public Color getColorBack() {
        return colorBack;
    }

    public void setColorBack(Color colorBack) {
        this.colorBack = colorBack;
    }

    public double getLineWeight() {
        return lineWeight;
    }

    public void setLineWeight(double lineWeight) {
        this.lineWeight = lineWeight;
    }

    @Override
    public TypeFigure type() {
        return TypeFigure.RECTANGLE;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setLineWidth(lineWeight);
        gc.setFill(colorBack);
        gc.setStroke(colorLine);
        gc.fillRect(position.getX(), position.getY(), w, h);
        gc.strokeRect(position.getX(), position.getY(), w, h);
    }

    @Override
    public String toJSONString() {
        return new JSONStringer()
                .object()
                .key("pos").value(position)
                .key("w").value(w)
                .key("h").value(h)
                .key("type").value(type().toString())
                .key("colorLine").value(colorLine.toString())
                .key("colorBack").value(colorBack.toString())
                .key("lineWeight").value(lineWeight)
                .endObject()
                .toString();
    }
}
