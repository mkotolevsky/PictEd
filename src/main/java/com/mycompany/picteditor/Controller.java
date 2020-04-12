package com.mycompany.picteditor;

import com.mycompany.picteditor.builder.PolygonBuilder;
import com.mycompany.picteditor.command.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller {
    public MenuItem openFileMenu;
    public MenuItem saveAsMenu;
    public MenuItem undoMenu;
    public MenuItem redoMenu;
    public ListView listFigures;
    public TextField xMove;
    public TextField yMove;
    public Button moveButton;
    public TextField lineWeightChange;
    public ColorPicker colorLineChange;
    public Button changeButton;
    public ColorPicker colorBackChange;
    public TextField xPoint;
    public TextField rPoint;
    public TextField yPoint;
    public ColorPicker colorPoint;
    public Button createPointButton;
    public TextField lineWeightLine;
    public ColorPicker colorLine;
    public Button createLineButton;
    public TextField xRect;
    public TextField yRect;
    public TextField lineWeightRect;
    public TextField widthRect;
    public TextField heightRect;
    public ColorPicker colorBackRect;
    public ColorPicker colorLineRect;
    public Button createRectButton;
    public TextField lineWeightPolygon;
    public ColorPicker colorLinePolygon;
    public ColorPicker colorBackPolygon;
    public Button createPolygon;
    public Canvas canvas;
    public Button removeButton;
    public TextField keyPoint;
    public TextField keyLine;
    public TextField keyRect;
    public TextField keyPolygon;
    public MenuItem clearMenu;
    public MenuItem startMenu;

    private Model model = new Model();
    private Switcher switcher;
    private List<Point> pointList = new ArrayList<>();
    private String key;
    private Map<String, Button> btns = new HashMap<>();

    public Controller() {
        switcher = new Switcher(model);
    }

    public void openFile(ActionEvent actionEvent) {
        File file = new FileChooser().showOpenDialog(new Stage());
        if (file != null) {
            try {
                JSONObject jo = new JSONObject(new String(Files.readAllBytes(file.toPath())));
                clear();
                model.copyParams(new JSONParse().parseModel(jo));
                for (String key :
                        model.setKeys()) {
                    Button btn = createBtn(key);
                    listFigures.getItems().add(btn);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveAs(ActionEvent actionEvent) {
        File file = new FileChooser().showOpenDialog(new Stage());
        if (file != null) {
            try(FileWriter writer = new FileWriter(file.getAbsolutePath(), false))
            {
                writer.write(model.toJSONString());
                writer.flush();
            }
            catch(IOException ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    public void undo(ActionEvent actionEvent) {
        switcher.undo();
    }

    public void redo(ActionEvent actionEvent) {
        switcher.toFuture();
    }

    public void move(ActionEvent actionEvent) {
        if (key == null || !model.contains(key)) return;
        double x, y;
        x = Double.parseDouble(xMove.getText());
        y = Double.parseDouble(yMove.getText());
        switcher.execute(new MoveCommand(key, new Point(x, y)));
    }

    public void change(ActionEvent actionEvent) {
        if (key == null || !model.contains(key)) return;
        Color colorBack = colorBackChange.getValue();
        Color colorLine = colorLineChange.getValue();
        double line = -1;
        try {
            line = Double.parseDouble(lineWeightChange.getText());
        } catch (NumberFormatException e) {

        }
        switcher.execute(new ChangeStateCommand(
                key,
                colorLine,
                colorBack,
                line
        ));
    }

    public void createPoint(ActionEvent actionEvent) {
        String key = keyPoint.getText();
        if (model.contains(key)) return;
        PointFigure f = new PointFigure(
                new Point(
                        Double.parseDouble(xPoint.getText()),
                        Double.parseDouble(yPoint.getText())
                ),
                colorPoint.getValue(),
                Double.parseDouble(rPoint.getText())
        );
        switcher.execute(new AddCommand(key, f, listFigures, createBtn(key)));
    }

    public void createLine(ActionEvent actionEvent) {
        String key = keyLine.getText();
        if (model.contains(key)) return;
        if (pointList.size() != 2) return;
        Point start, end, pos;
        start = pointList.get(0);
        end = pointList.get(1);
        pos = new Point(
                (start.getX() + end.getX()) / 2,
                (start.getY() + end.getY()) / 2
        );
        start = start.minus(pos);
        end = end.minus(pos);
        Line f = new Line(
                pos, start, end,
                colorLine.getValue(),
                Double.parseDouble(lineWeightLine.getText())
        );
        switcher.execute(new AddCommand(key, f, listFigures, createBtn(key)));
    }

    public void createRect(ActionEvent actionEvent) {
        String key = keyRect.getText();
        if (model.contains(key)) return;
        Rectangle f = new Rectangle(
                new Point(
                        Double.parseDouble(xRect.getText()),
                        Double.parseDouble(yRect.getText())
                ),
                Double.parseDouble(widthRect.getText()),
                Double.parseDouble(heightRect.getText()),
                colorLineRect.getValue(),
                colorBackRect.getValue(),
                Double.parseDouble(lineWeightRect.getText())
        );
        switcher.execute(new AddCommand(key, f, listFigures, createBtn(key)));
    }

    public void createPolygon(ActionEvent actionEvent) {
        String key = keyPolygon.getText();
        if (model.contains(key)) return;
        PolygonBuilder pb = new PolygonBuilder();
        double x = 0, y = 0;
        for (Point p :
                pointList) {
            x += p.getX();
            y += p.getY();
        }
        x /= pointList.size();
        y /= pointList.size();
        Point pos = new Point(x, y);
        for (Point p :
                pointList) {
            pb.addPoint(p.minus(pos));
        }
        pb.setPosition(pos)
                .setColorBack( colorLinePolygon.getValue())
                .setColorLine(colorBackPolygon.getValue())
                .setLineWeight(Double.parseDouble(lineWeightPolygon.getText()));
        switcher.execute(new AddCommand(key, pb.build(), listFigures, createBtn(key)));
    }

    public void touchPoint(MouseEvent mouseEvent) {
        pointList.add(
                new Point(
                        mouseEvent.getX(),
                        mouseEvent.getY()
                )
        );
        System.out.println(new Point(
                mouseEvent.getX(),
                mouseEvent.getY()
        ));
    }

    private Button createBtn(final String key) {
        Button button = new Button(key);
        button.setOnAction(e ->  {
            this.key = key;
            Figure f = model.get(key);
            xMove.setText(String.valueOf(f.getPosition().getX()));
            yMove.setText(String.valueOf(f.getPosition().getY()));
            switch(f.type()) {
                case POINT: {
                    PointFigure cf = (PointFigure) f;
                    colorBackChange.setValue(cf.getC());
                }
                break;
                case LINE:
                {
                    Line cf = (Line) f;
                    colorLineChange.setValue(cf.getC());
                    lineWeightChange.setText(String.valueOf(cf.getLineWeight()));
                }
                break;
                case RECTANGLE: {
                    Rectangle rf = (Rectangle) f;
                    colorBackChange.setValue(rf.getColorBack());
                    colorLineChange.setValue(rf.getColorLine());
                    lineWeightChange.setText(String.valueOf(rf.getLineWeight()));
                    break;
                }
                case POLYGON: {
                    Polygon rf = (Polygon) f;
                    colorBackChange.setValue(rf.getColorBack());
                    colorLineChange.setValue(rf.getColorLine());
                    lineWeightChange.setText(String.valueOf(rf.getLineWeight()));
                    break;
                }
            }
        });
        btns.put(key, button);
        return button;
    }

    public void chooseMove(Event event) {
        key = null;
        clearData();
    }

    private void clearData() {
        colorBackChange.setValue(Color.WHITE);
        colorLineChange.setValue(Color.WHITE);
        lineWeightChange.setText("");
        xMove.setText("");
        yMove.setText("");
    }

    public void remove(ActionEvent actionEvent) {
        if (key == null) return;
        switcher.execute(new RemoveCommand(key, listFigures, btns.get(key)));
    }

    public void clearPoints(ActionEvent actionEvent) {
        pointList.clear();
        System.out.println("Points clear");
    }

    public void draw(ActionEvent actionEvent) {
        Runnable r = ()->{
            while (true) {
                model.draw(canvas);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("Thread has been interrupted");
                }
            }
        };
        Thread myThread = new Thread(r,"Drawing");
        myThread.setDaemon(true);
        myThread.start();
    }

    private void clear() {
        listFigures.getItems().clear();
        model.clear();
        switcher.clear();
    }
}
