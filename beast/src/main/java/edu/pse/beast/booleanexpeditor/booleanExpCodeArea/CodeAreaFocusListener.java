package edu.pse.beast.booleanexpeditor.booleanExpCodeArea;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * FocusListener given to the JTextPanes of the BooleanExpEditorWindow JFrame.
 * getLastFocused returns the CodeArea belonging to the last focused JTextPane.
 * @author NikolaiLMS
 */
public class CodeAreaFocusListener implements FocusListener {
    private BooleanExpCodeArea lastFocused;
    private BooleanExpCodeArea prePropCodeArea;
    private BooleanExpCodeArea postPropCodeArea;

    public CodeAreaFocusListener(BooleanExpCodeArea prePropCodeArea, BooleanExpCodeArea postPropCodeArea) {
        lastFocused = prePropCodeArea;
        this.prePropCodeArea = prePropCodeArea;
        this.postPropCodeArea = postPropCodeArea;
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
