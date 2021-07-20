package edu.pse.beast.gui.ceditor;

import org.fxmisc.richtext.CodeArea;

import edu.pse.beast.gui.CodeAreaChangeListener;

public class CEditorCodeElement extends CodeArea {
    private CodeAreaChangeListener changeListener;

    public CEditorCodeElement() {
        setOnKeyTyped(e -> {
            changeListener.codeChanged(getText());
        });
    }

    public void setChangeListener(final CodeAreaChangeListener codeAreaChangeListener) {
        this.changeListener = codeAreaChangeListener;
    }
}
