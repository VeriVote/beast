package edu.pse.beast.highlevel.javafx;

import edu.pse.beast.saverloader.MinimalSaverInterface;

/**
 * The Interface MenuBarInterface.
 *
 * @author Lukas Stapelbroek
 */
public interface MenuBarInterface extends MinimalSaverInterface {

    /**
     * Open.
     */
    void open();

    /**
     * Undo.
     */
    void undo();

    /**
     * Redo.
     */
    void redo();

    /**
     * Cut.
     */
    void cut();

    /**
     * Copy.
     */
    void copy();

    /**
     * Paste.
     */
    void paste();

    /**
     * Delete.
     */
    void delete();

    /**
     * Auto complete.
     */
    void autoComplete();
}
