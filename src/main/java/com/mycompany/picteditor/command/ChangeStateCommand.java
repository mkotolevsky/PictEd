package com.mycompany.picteditor.command;

import com.mycompany.picteditor.*;
import javafx.scene.paint.Color;

public class ChangeStateCommand implements Command {

    private String key;
    private FigureState prev, next;
    private Model model;

    private static class FigureState {
        private Color colorLine, colorBack;
        private double lineWeight;

        public FigureState(Color colorLine, Color colorBack, double lineWeight) {
            this.colorLine = colorLine;
            this.colorBack = colorBack;
            this.lineWeight = lineWeight;
        }
    }

    public ChangeStateCommand(String key, Color colorLine, Color colorBack, double lineWeight) {
        this.key = key;
        next = new FigureState(colorLine, colorBack, lineWeight);
    }

    @Override
    public void execute() {
        Figure o = model.get(key);
        switch (o.type()) {
            case POINT: {
                PointFigure f = (PointFigure) o;
                prev = new FigureState(null, f.getC(), -1);
                f.setC(next.colorBack);
                break;
            }
            case LINE: {
                Line f = (Line) o;
                prev = new FigureState( f.getC(), null, f.getLineWeight());
                f.setC(next.colorLine);
                f.setLineWeight(next.lineWeight);
                break;
            }
            case RECTANGLE: {
                Rectangle f = (Rectangle) o;
                prev = new FigureState(f.getColorLine(), f.getColorBack(), f.getLineWeight());
                f.setColorBack(next.colorBack);
                f.setColorLine(next.colorLine);
                f.setLineWeight(next.lineWeight);
                break;
            }
            case POLYGON: {
                Polygon f = (Polygon) o;
                prev = new FigureState(f.getColorLine(), f.getColorBack(), f.getLineWeight());
                f.setColorBack(next.colorBack);
                f.setColorLine(next.colorLine);
                f.setLineWeight(next.lineWeight);
                break;
            }
        }
    }

    @Override
    public void undo() {
        Figure o = model.get(key);
        switch (o.type()) {
            case POINT: {
                PointFigure f = (PointFigure) o;
                f.setC(prev.colorBack);
                break;
            }
            case LINE: {
                Line f = (Line) o;
                f.setC(prev.colorLine);
                f.setLineWeight(prev.lineWeight);
                break;
            }
            case RECTANGLE: {
                Rectangle f = (Rectangle) o;
                f.setColorBack(prev.colorBack);
                f.setColorLine(prev.colorLine);
                f.setLineWeight(prev.lineWeight);
                break;
            }
            case POLYGON: {
                Polygon f = (Polygon) o;
                f.setColorBack(prev.colorBack);
                f.setColorLine(prev.colorLine);
                f.setLineWeight(prev.lineWeight);
                break;
            }
        }
    }

    @Override
    public void setModel(Model model) {
        this.model = model;
    }
}
