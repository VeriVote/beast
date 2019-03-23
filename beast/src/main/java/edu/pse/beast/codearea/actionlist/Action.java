package edu.pse.beast.codearea.actionlist;

/**
 *
 * @author Holger Klein
 */
public interface Action {
    void undo();

    void redo();
}