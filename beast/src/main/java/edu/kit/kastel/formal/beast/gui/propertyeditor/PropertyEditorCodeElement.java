package edu.kit.kastel.formal.beast.gui.propertyeditor;

import org.fxmisc.richtext.CodeArea;

import edu.kit.kastel.formal.beast.gui.CodeAreaChangeListener;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
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
