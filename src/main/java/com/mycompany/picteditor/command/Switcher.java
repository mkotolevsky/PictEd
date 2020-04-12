package com.mycompany.picteditor.command;

import com.mycompany.picteditor.Model;

import java.util.Stack;

public class Switcher {

    private Stack<Command> executed;
    private Stack<Command> future;
    private Model model;

    public Switcher(Model model) {
        executed = new Stack<>();
        future = new Stack<>();
        this.model = model;
    }

    public void execute(Command command) {
        command.setModel(model);
        future.clear();
        command.execute();
        executed.add(command);
    }

    public void undo() {
        if (executed.empty()) return;
        Command c = executed.pop();
        c.undo();
        future.add(c);
    }

    public void toFuture() {
        if (!future.empty()) {
            Command c = future.pop();
            c.execute();
            executed.add(c);
        }
    }

    public void clear() {
        executed.clear();
        future.clear();
    }
}
