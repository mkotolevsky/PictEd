package com.mycompany.picteditor;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.json.JSONStringer;
import org.json.JSONWriter;

import java.util.ArrayList;
import java.util.List;

public class Polygon extends AbstractFigure {

    private List<Point> points;
    private Color colorLine, colorBack;
    private double lineWeight;

    public Polygon(Point position, List<Point> points, Color colorLine, Color colorBack, double lineWeight) {
        super(position);
        this.points = new ArrayList<>(points);
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
        return TypeFigure.POLYGON;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setLineWidth(lineWeight);
        double[] x = new double[points.size()];
        double[] y = new double[points.size()];
        for (int i = 0; i < points.size(); i++) {
            Point p = points.get(i).plus(position);
            x[i] = p.getX();
            y[i] = p.getY();
        }
        gc.setStroke(colorLine);
        gc.setFill(colorBack);
        gc.fillPolygon(x, y, x.length);
        gc.strokePolygon(x, y, x.length);
    }

    @Override
    public String toJSONString() {
        JSONWriter jw =  new JSONStringer()
                .object()
                .key("pos").value(position)
                .key("type").value(type().toString())
                .key("colorLine").value(colorLine.toString())
                .key("colorBack").value(colorBack.toString())
                .key("lineWeight").value(lineWeight)
                .key("points").array();
        for (int i = 0; i < points.size(); i++) {
            jw.value(points.get(i));
        }
        return jw.endArray().endObject().toString();
    }
}
