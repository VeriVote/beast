package edu.pse.beast.gui.propertyeditor;

import org.fxmisc.richtext.CodeArea;

import edu.pse.beast.gui.CodeAreaChangeListener;

public class PropertyEditorCodeElement extends CodeArea {

    private CodeAreaChangeListener changeListener;

    public PropertyEditorCodeElement() {
        setOnKeyTyped(e -> {
            changeListener.codeChanged(getText());
        });
    }

    public final void setChangeListener(final CodeAreaChangeListener codeAreaChangeListener) {
        this.changeListener = codeAreaChangeListener;
    }
}
