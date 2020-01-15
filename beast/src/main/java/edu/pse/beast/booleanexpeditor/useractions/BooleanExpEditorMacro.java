package edu.pse.beast.booleanexpeditor.useractions;

/**
 * This class models Macros for the BooleanExpEditor.
 *
 * @author Nikolai Schnell
 */
public class BooleanExpEditorMacro {
    private final String macro;

    /**
     * Constructor.
     *
     * @param macroString the macro in form of a String
     */
    public BooleanExpEditorMacro(final String macroString) {
        this.macro = macroString;
    }

    @Override
    public String toString() {
        return macro;
    }
}
