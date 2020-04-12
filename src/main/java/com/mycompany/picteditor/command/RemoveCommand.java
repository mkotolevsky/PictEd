package com.mycompany.picteditor.command;

import com.mycompany.picteditor.Figure;
import com.mycompany.picteditor.Model;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class RemoveCommand implements Command {

    private Model model;
    private String key;
    private Figure figure;
    private ListView listView;
    private Button btn;

    public RemoveCommand(String key, ListView list, Button btn) {
        this.key = key;
        this.listView = list;
        this.btn = btn;
    }

    @Override
    public void execute() {
        figure = model.remove(key);
        listView.getItems().remove(btn);
    }

    @Override
    public void undo() {
        model.put(key, figure);
        listView.getItems().add(btn);
    }

    @Override
    public void setModel(Model model) {
        this.model = model;
    }
}
