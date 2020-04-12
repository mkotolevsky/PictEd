package com.mycompany.picteditor;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.json.JSONString;

import java.util.List;

public interface Figure extends JSONString {

    Point getPosition();

    Point setPosition(Point p);

    TypeFigure type();

    void draw(GraphicsContext gc);
}
