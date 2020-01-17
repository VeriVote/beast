package edu.pse.beast.toolbox.valueContainer;

/**
 * The Class ResultValueWrapper.
 *
 * @author Lukas Stapelbroek
 */
public abstract class ResultValueWrapper {

    /** The main index. */
    private final int mainIndex;

    /** The name. */
    private final String name;

    /** The is top level. */
    private final boolean isTopLevel;

    /**
     * Instantiates a new result value wrapper.
     *
     * @param mainIdx
     *            the main idx
     * @param nameString
     *            the name string
     */
    public ResultValueWrapper(final int mainIdx, final String nameString) {
        this.mainIndex = mainIdx;
        this.name = nameString;
        this.isTopLevel = true;
    }

    /**
     * Instantiates a new result value wrapper.
     */
    public ResultValueWrapper() {
        this.mainIndex = 0;
        this.name = "";
        this.isTopLevel = false;
    }

    /**
     * Gets the main index.
     *
     * @return returns the main index (for example votes1 has the mainIndex of
     *         1)
     */
    public int getMainIndex() {
        if (!isTopLevel) {
            throw new IllegalArgumentException("This object has no"
                                                + " valid main index.");
        }
        return mainIndex;
    }

    /**
     * Gets the name.
     *
     * @return the name of the var (for example votes1 has the name votes)
     */
    public String getName() {
        if (!isTopLevel) {
            throw new IllegalArgumentException("This object has no"
                                                + " valid name.");
        }
        return name;
    }

    /**
     * Gets the result value.
     *
     * @return the result value
     */
    public abstract ResultValue getResultValue();
}
