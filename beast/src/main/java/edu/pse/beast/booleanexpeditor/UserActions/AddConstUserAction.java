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
        int lastCaretPosition = booleanExpEditor.getCodeAreaFocusListener().getLastCaretPosition();
        try {
            JTextPane textPane = booleanExpEditor.getCodeAreaFocusListener().getLastFocused().getPane();
            textPane.getStyledDocument().insertString(lastCaretPosition,
                    booleanExpEditorConst.getConstantString(), new AttributeSet() {
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
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
