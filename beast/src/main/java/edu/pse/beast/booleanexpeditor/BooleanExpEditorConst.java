package edu.pse.beast.booleanexpeditor;

/**
 * This class models constants for the BooleanExpEditor, namely the number of voters "V", the number of candidates "C".
 * and the number of seats "S".
 * @author NikolaiLMS
 */
public class BooleanExpEditorConst {
    private String constant;

    /**
     * String should be either "Voters", "Candidates" or "Seats"
     * @param constant the String representing the constant, either "V", "C" or "S"
     */
    BooleanExpEditorConst(String constant) {
        this.constant = constant;
    }

    public String getConstantString() {
        return constant;
    }
}
