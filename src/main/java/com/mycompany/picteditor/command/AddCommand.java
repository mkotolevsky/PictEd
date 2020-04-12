package com.mycompany.picteditor.command;

import com.mycompany.picteditor.Figure;
import com.mycompany.picteditor.Model;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class AddCommand implements Command {

    private Model model;
    private String key;
    private Figure figure;
    private ListView list;
    private Button btn;

    public AddCommand(String key, Figure figure, ListView list, Button btn) {
        this.key = key;
        this.figure = figure;
        this.list = list;
        this.btn = btn;
    }

    @Override
    public void execute() {
        model.put(key, figure);
        list.getItems().add(btn);
    }

    @Override
    public void undo() {
        model.remove(key);
        list.getItems().remove(btn);
    }

    @Override
    public void setModel(Model model) {
        this.model = model;
    }
}
