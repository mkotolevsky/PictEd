package com.mycompany.picteditor.command;

import com.mycompany.picteditor.Figure;
import com.mycompany.picteditor.Model;
import com.mycompany.picteditor.Point;

public class MoveCommand implements Command {

    private Point prev, next;
    private String key;
    private Model model;

    public MoveCommand(String key, Point next) {
        this.next = next;
        this.key = key;
    }

    @Override
    public void execute() {
        Figure f = model.get(key);
        prev = f.getPosition();
        f.setPosition(next);
    }

    @Override
    public void undo() {
        Figure f = model.get(key);
        f.setPosition(prev);
    }

    @Override
    public void setModel(Model model) {
        this.model = model;
    }
}
