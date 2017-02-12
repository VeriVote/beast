package edu.pse.beast.booleanexpeditor.UserActions;

/**
 * This class models Makros for the BooleanExpEditor.
 * @author NikolaiLMS
 */
public class BooleanExpEditorMakro {

    private final String makro;

    /**
     * Constructor
     * @param makro the makro in form of a String
     */
    public BooleanExpEditorMakro(String makro) {
        this.makro = makro;
    }

    public String toString() {
        return makro;
    }
}
