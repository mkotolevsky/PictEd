package com.mycompany.picteditor;

public abstract class AbstractFigure implements Figure {

    protected Point position;

    public AbstractFigure(Point position) {
        this.position = position;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public Point setPosition(Point p) {
        Point old = position;
        position = p;
        return old;
    }
}
