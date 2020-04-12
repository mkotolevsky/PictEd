package com.mycompany.picteditor.command;

import com.mycompany.picteditor.Model;

public interface Command {

    void execute();

    void undo();

    void setModel(Model model);
}
