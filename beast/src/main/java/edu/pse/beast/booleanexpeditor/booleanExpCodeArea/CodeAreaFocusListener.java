package edu.pse.beast.booleanexpeditor.booleanExpCodeArea;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.codearea.CodeArea;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * @author NikolaiLMS
 */
public class CodeAreaFocusListener implements FocusListener {
    private BooleanExpCodeArea lastFocused;
    private int lastCaretPosition;
    private BooleanExpCodeArea prePropCodeArea;
    private BooleanExpCodeArea postPropCodeArea;

    public CodeAreaFocusListener(BooleanExpCodeArea prePropCodeArea, BooleanExpCodeArea postPropCodeArea) {
        lastFocused = prePropCodeArea;
        lastCaretPosition = prePropCodeArea.getPane().getCaretPosition();
        this.prePropCodeArea = prePropCodeArea;
        this.postPropCodeArea = postPropCodeArea;
    }

    @Override
    public void focusGained(FocusEvent focusEvent) {
        if (((JTextPane) focusEvent.getComponent()).equals(prePropCodeArea)) {
            lastFocused = prePropCodeArea;
        } else {
            lastFocused = postPropCodeArea;
        }
        lastCaretPosition = lastFocused.getPane().getCaretPosition();
    }

    @Override
    public void focusLost(FocusEvent focusEvent) {    }

    public BooleanExpCodeArea getLastFocused() {
        return lastFocused;
    }

    public int getLastCaretPosition(){
        return lastCaretPosition;
    }
}
