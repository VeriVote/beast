package edu.pse.beast.propertychecker;

/**
 * The Class SymbolicVarNameAndNumber.
 *
 * @author Lukas Stapelbroek
 */
public class SymbolicVarNameAndNumber {

    /** The name. */
    private final String name;

    /** The number. */
    private final long number;

    /**
     * This class wraps a name for a symbolic variable and the number that the
     * checker determined for this candidate.
     *
     * @param nam the variable name
     * @param num the determined number
     */
    public SymbolicVarNameAndNumber(final String nam, final long num) {
        this.name = nam;
        this.number = num;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the number.
     *
     * @return the number
     */
    public long getNumber() {
        return number;
    }
}
