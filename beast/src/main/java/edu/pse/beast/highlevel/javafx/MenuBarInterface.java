package edu.pse.beast.highlevel.javafx;

import edu.pse.beast.saverloader.MinimalSaverInterface;

public interface MenuBarInterface extends MinimalSaverInterface {
    void open();

    void undo();

    void redo();

    void cut();

    void copy();

    void paste();

    void delete();

    void autoComplete();
}
