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
        int lastCaretPosition = booleanExpEditor.getCodeAreaFocusListener().getLastCaretPosition();
        try {
            JTextPane textPane = booleanExpEditor.getCodeAreaFocusListener().getLastFocused().getPane();

            textPane.getStyledDocument().insertString(lastCaretPosition,
                    makro.toString() , new AttributeSet() {
                        @Override
                        public int getAttributeCount() {
                            return 0;
                        }

                        @Override
                        public boolean isDefined(Object o) {
                            return false;
                        }

                        @Override
                        public boolean isEqual(AttributeSet attributeSet) {
                            return false;
                        }

                        @Override
                        public AttributeSet copyAttributes() {
                            return null;
                        }

                        @Override
                        public Object getAttribute(Object o) {
                            return null;
                        }

                        @Override
                        public Enumeration<?> getAttributeNames() {
                            return null;
                        }

                        @Override
                        public boolean containsAttribute(Object o, Object o1) {
                            return false;
                        }

                        @Override
                        public boolean containsAttributes(AttributeSet attributeSet) {
                            return false;
                        }

                        @Override
                        public AttributeSet getResolveParent() {
                            return null;
                        }
                    });
            String temp = textPane.getStyledDocument().getText(0, textPane.getStyledDocument().getLength());
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
