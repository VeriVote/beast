package edu.pse.beast.api.method;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public enum CElectionSimpleType {
    INT, UNSIGNED_INT, FLOAT, DOUBLE, VOID, BOOL;

    private static final String BLANK = " ";
    private static final String UNDERSCORE = "_";

    @Override
    public String toString() {
        return this.name()
                .replaceAll(BOOL.name(), UNSIGNED_INT.name())
                .toLowerCase()
                .replaceAll(UNDERSCORE, BLANK);
    }
}
