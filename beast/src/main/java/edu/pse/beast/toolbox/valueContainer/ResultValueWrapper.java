package edu.pse.beast.toolbox.valueContainer;

public abstract class ResultValueWrapper {
    private final int mainIndex;
    private final String name;
    private final boolean isTopLevel;

    public ResultValueWrapper(int mainIndex, String name) {
        this.mainIndex = mainIndex;
        this.name = name;
        this.isTopLevel = true;
    }

    public ResultValueWrapper() {
        this.mainIndex = 0;
        this.name = "";
        this.isTopLevel = false;
    }

    /**
     *
     * @return returns the main index (for example votes1 has the mainIndex of
     *         1)
     */
    public int getMainIndex() {
        if (!isTopLevel) {
            throw new IllegalArgumentException("This object has no valid main index");
        }
        return mainIndex;
    }

    /**
     *
     * @return the name of the var (for example votes1 has the name votes)
     */
    public String getName() {
        if (!isTopLevel) {
            throw new IllegalArgumentException("This object has no valid name");
        }
        return name;
    }

    public abstract ResultValue getResultValue();
}
