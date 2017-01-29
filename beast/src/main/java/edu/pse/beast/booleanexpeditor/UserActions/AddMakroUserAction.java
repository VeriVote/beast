package edu.pse.beast.booleanexpeditor.UserActions;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.toolbox.UserAction;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import java.util.Enumeration;

/**
 * @author NikolaiLMS
 */
public class AddMakroUserAction extends UserAction {
    private BooleanExpEditorMakro makro;
    private BooleanExpEditor booleanExpEditor;

    /**
     * Constructor
     * @param makro the BooleanExpEditorMakro object this UserAction adds
     */
    public AddMakroUserAction(String id, BooleanExpEditorMakro makro, BooleanExpEditor booleanExpEditor) {
        super(id);
        this.makro = makro;
        this.booleanExpEditor = booleanExpEditor;
    }

    @Override
    public void perform() {
        int lastCaretPosition = booleanExpEditor.getCodeAreaFocusListener().getLastFocused().getPane().getCaretPosition();
        try {
            JTextPane textPane = booleanExpEditor.getCodeAreaFocusListener().getLastFocused().getPane();
            textPane.getStyledDocument().insertString(lastCaretPosition,
                    makro.toString() ,null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

    }

    /**
     * Getter
     * @return makro
     */
    public BooleanExpEditorMakro getMakro() {
        return makro;
    }
}
