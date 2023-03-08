package edu.kit.kastel.formal.beast.gui.ceditor;

import org.fxmisc.richtext.CodeArea;

import edu.kit.kastel.formal.beast.gui.CodeAreaChangeListener;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CEditorCodeElement extends CodeArea {
    private CodeAreaChangeListener changeListener;

    public CEditorCodeElement() {
        setOnKeyTyped(e -> {
            changeListener.codeChanged(getText());
        });
    }

    public final void setChangeListener(final CodeAreaChangeListener codeAreaChangeListener) {
        this.changeListener = codeAreaChangeListener;
    }
}
