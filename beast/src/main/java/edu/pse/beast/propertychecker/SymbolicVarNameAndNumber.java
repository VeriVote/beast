package edu.pse.beast.propertychecker;

/**
 *
 * @author Lukas Stapelbroek
 *
 */
public class SymbolicVarNameAndNumber {

    private final String name;
    private final long number;

    /**
     * this class wraps a name for a symbolic variable and the number that the
     * checker determined for this candidate
     *
     * @param name
     * @param number
     */
    public SymbolicVarNameAndNumber(String name, long number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public long getNumber() {
        return number;
    }
}