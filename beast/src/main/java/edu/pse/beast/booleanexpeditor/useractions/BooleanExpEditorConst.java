package edu.pse.beast.booleanexpeditor.useractions;

import edu.pse.beast.toolbox.UnifiedNameContainer;

/**
 * This class models constants for the BooleanExpEditor, namely the number of
 * voters "V", the number of candidates "C" and the number of seats "S".
 *
 * @author Nikolai Schnell
 */
public class BooleanExpEditorConst {

    /** The constant. */
    private final String constant;

    /**
     * String should be either "Voters", "Candidates" or "Seats".
     *
     * @param constantString the String representing the constant, either "V", "C" or "S"
     */
    public BooleanExpEditorConst(final String constantString) {
        this.constant = constantString;
    }

    @Override
    public String toString() {
        return UnifiedNameContainer.getByKey(constant);
    }
}
