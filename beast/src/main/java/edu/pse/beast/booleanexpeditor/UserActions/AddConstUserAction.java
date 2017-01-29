package edu.pse.beast.booleanexpeditor.UserActions;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.booleanexpeditor.BooleanExpEditorConst;
import edu.pse.beast.booleanexpeditor.BooleanExpEditorWindow;
import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.BooleanExpCodeArea;
import edu.pse.beast.toolbox.UserAction;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import java.util.Enumeration;

/**
 * @author NikolaiLMS
 */
public class AddConstUserAction extends UserAction {
    private BooleanExpEditorConst booleanExpEditorConst;
    private BooleanExpEditor booleanExpEditor;

    /**
     * Constructor
     * @param booleanExpEditorConst the BooleanExpEditorConst object this UserAction adds
     */
    public AddConstUserAction(String id, BooleanExpEditorConst booleanExpEditorConst, BooleanExpEditor booleanExpEditor) {
        super("numberOf" + id);
        this.booleanExpEditor = booleanExpEditor;
        this.booleanExpEditorConst = booleanExpEditorConst;
    }

    @Override
    public void perform() {
        int lastCaretPosition = booleanExpEditor.getCodeAreaFocusListener().getLastFocused().getPane().getCaretPosition();
        try {
            JTextPane textPane = booleanExpEditor.getCodeAreaFocusListener().getLastFocused().getPane();
            textPane.getStyledDocument().insertString(lastCaretPosition,
                    booleanExpEditorConst.getConstantString(), null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
