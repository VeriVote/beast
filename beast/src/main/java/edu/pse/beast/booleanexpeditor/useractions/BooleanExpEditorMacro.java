package edu.pse.beast.booleanexpeditor.useractions;

/**
 * This class models Macros for the BooleanExpEditor.
 *
 * @author Nikolai Schnell
 */
public class BooleanExpEditorMacro {
    private final String macro;

    /**
     * Constructor
     *
     * @param macro the macro in form of a String
     */
    public BooleanExpEditorMacro(String macro) {
        this.macro = macro;
    }

    @Override
    public String toString() {
        return macro;
    }
}