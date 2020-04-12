package com.mycompany.picteditor;

import javafx.scene.paint.Color;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONParse {

    public Model parseModel(JSONObject jo) {
        Model model = new Model(Color.valueOf(jo.getString("color")));
        JSONObject jsonFigures = jo.getJSONObject("figures");
        for (String key :
                jsonFigures.keySet()) {
            model.put(key, parseFigure(jsonFigures.getJSONObject(key)));
        }
        return model;
    }

    public Figure parseFigure(JSONObject jo) {
        TypeFigure type = TypeFigure.valueOf(jo.getString("type"));
        switch (type) {
            case POINT:
                return parseFigurePoint(jo);
            case LINE:
                return parseLine(jo);
            case RECTANGLE:
                return parseRectangle(jo);
            case POLYGON:
                return parsePolygon(jo);
            default:
                throw new IllegalArgumentException();
        }
    }

    private Figure parseFigurePoint(JSONObject jo) {
        return new PointFigure(
                parsePoint(jo.getJSONObject("pos")),
                Color.valueOf(jo.getString("color")),
                jo.getDouble("radius")
        );
    }

    private Figure parseRectangle(JSONObject jo) {
        return new Rectangle(
                parsePoint(jo.getJSONObject("pos")),
                jo.getDouble("w"),
                jo.getDouble("h"),
                Color.valueOf(jo.getString("colorLine")),
                Color.valueOf(jo.getString("colorBack")),
                jo.getDouble("lineWeight")
        );
    }

    private Figure parseLine(JSONObject jo) {
        return new Line(
                parsePoint(jo.getJSONObject("pos")),
                parsePoint(jo.getJSONObject("start")),
                parsePoint(jo.getJSONObject("end")),
                Color.valueOf(jo.getString("color")),
                jo.getDouble("lineWeight")
        );
    }

    private Figure parsePolygon(JSONObject jo) {
        List<Point> points = new ArrayList<>();
        JSONArray arr = jo.getJSONArray("points");
        for (int i = 0; i < arr.length(); i++) {
            points.add(parsePoint(arr.getJSONObject(i)));
        }
        return new Polygon(
                parsePoint(jo.getJSONObject("pos")),
                points,
                Color.valueOf(jo.getString("colorLine")),
                Color.valueOf(jo.getString("colorBack")),
                jo.getDouble("lineWeight")
        );
    }

    private Point parsePoint(JSONObject jo) {
        Point p = new Point();
        p.setX(jo.getDouble("x"));
        p.setY(jo.getDouble("y"));
        return p;
    }
}
