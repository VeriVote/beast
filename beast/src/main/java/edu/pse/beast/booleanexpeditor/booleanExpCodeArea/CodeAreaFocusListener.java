package edu.pse.beast.booleanexpeditor.booleanExpCodeArea;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * FocusListener given to the JTextPanes of the BooleanExpEditorWindow JFrame.
 * getLastFocused returns the CodeArea belonging to the last focused JTextPane.
 * Implements FocusListener so it can be applied to the JTextPanes of the CodeAreas.
 * @author NikolaiLMS
 */
public class CodeAreaFocusListener implements FocusListener {
    private BooleanExpCodeArea lastFocused;
    private BooleanExpCodeArea prePropCodeArea;
    private BooleanExpCodeArea postPropCodeArea;

    /**
     * Constructor
     * @param prePropCodeArea CodeArea for pre conditions
     * @param postPropCodeArea CodeArea for post conditions
     */
    public CodeAreaFocusListener(BooleanExpCodeArea prePropCodeArea, BooleanExpCodeArea postPropCodeArea) {
        lastFocused = prePropCodeArea;
        this.prePropCodeArea = prePropCodeArea;
        this.postPropCodeArea = postPropCodeArea;
    }

    /**
     * Method that sets new BooleanExpCodeAreas
     * @param prePropCodeArea the new BooleanExpCodeArea for pre-conditions
     * @param postPropCodeArea the new BooleanExpCodeArea for post-conditions
     */
    public void addNewCodeAreas(BooleanExpCodeArea prePropCodeArea, BooleanExpCodeArea postPropCodeArea) {
        this.prePropCodeArea = prePropCodeArea;
        this.postPropCodeArea = postPropCodeArea;
        lastFocused = prePropCodeArea;
    }

    @Override
    public void focusGained(FocusEvent focusEvent) {
        if ((focusEvent.getComponent()).equals(prePropCodeArea.getPane())) {
            lastFocused = prePropCodeArea;
        } else {
            lastFocused = postPropCodeArea;
        }
    }

    @Override
    public void focusLost(FocusEvent focusEvent) {
    }

    public BooleanExpCodeArea getLastFocused() {
        return lastFocused;
    }

}
