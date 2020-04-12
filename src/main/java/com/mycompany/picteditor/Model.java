package com.mycompany.picteditor;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.json.JSONString;
import org.json.JSONStringer;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Model implements JSONString {

    private  Map<String, Figure> figures;
    private Color colorBack = Color.WHITE;

    public Model(Color colorBack) {
        this.colorBack = colorBack;
        figures = new LinkedHashMap<>();
    }

    public Model() {
        figures = new LinkedHashMap<>();
    }

    public Collection<Figure> getFigures() {
        return figures.values();
    }

    public void put(String key, Figure figure) {
        figures.put(key, figure);
    }

    public Figure get(String key) {
        return figures.get(key);
    }

    public Figure remove(String key) {
        return figures.remove(key);
    }

    public boolean contains(String key) {
        return figures.containsKey(key);
    }

    public Set<String> setKeys() {
        return figures.keySet();
    }

    public void clear() {
        figures.clear();
    }

    public void copyParams(Model model) {
        figures = model.figures;
        colorBack = model.colorBack;
    }

    public void draw(Canvas c) {
        GraphicsContext gc = c.getGraphicsContext2D();
        gc.setFill(colorBack);
        gc.fillRect(0, 0, c.getWidth(), c.getHeight());
        figures.values().forEach(f -> f.draw(gc));
    }

    @Override
    public String toJSONString() {
        JSONStringer js = new JSONStringer();
        js.object()
                .key("color").value(colorBack.toString())
                .key("figures").object();
        for (Map.Entry<String, Figure> e :
                figures.entrySet()) {
            js.key(e.getKey()).value(e.getValue());
        }
        return js.endObject().endObject().toString();
    }
}
