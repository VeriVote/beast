package edu.kit.kastel.formal.beast.gui.run;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class OutputTextElement extends CodeArea {
    public OutputTextElement() {
        setEditable(false);
        setParagraphGraphicFactory(LineNumberFactory.get(this));
    }
}
