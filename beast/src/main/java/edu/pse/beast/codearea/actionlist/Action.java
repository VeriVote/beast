package edu.pse.beast.codearea.actionlist;

/**
 * The Interface Action.
 *
 * @author Holger Klein
 */
public interface Action {

    /**
     * Undo.
     */
    void undo();

    /**
     * Redo.
     */
    void redo();
}
