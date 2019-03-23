package edu.pse.beast.propertychecker;

/**
 * This class saves a long value from CBMC, including the name
 *
 * @author Lukas
 *
 */
public class CBMCResultWrapperLong {
    private final int mainIndex;
    private final String name;
    private String value = "-1";

    /**
     * creates a new wrapper
     *
     * @param mainIndex the index of this variable (for example votes1 has the main
     *                  index of 1)
     * @param name      the name of this variable (for example votes1 has the name
     *                  votes)
     */
    public CBMCResultWrapperLong(int mainIndex, String name) {
        this.mainIndex = mainIndex;
        this.name = name;
    }

    /**
     *
     * @param value the value to be set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     *
     * @return the index of this variable (for example votes1 has the main index of
     *         1)
     */
    public int getMainIndex() {
        return mainIndex;
    }

    /**
     *
     * @return the name of this variable (for example votes1 has the name votes)
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }
}