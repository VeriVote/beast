package edu.pse.beast.booleanexpeditor;

/**
 * @author NikolaiLMS
 */
public class BooleanExpEditorConst {
    private String constant;

    /**
     * String should be either "Voters", "Candidates" or "Seats"
     * @param constant
     */
    public BooleanExpEditorConst(String constant) {
        this.constant = constant;
    }

    public String getConstantString() {
        return constant;
    }
}
