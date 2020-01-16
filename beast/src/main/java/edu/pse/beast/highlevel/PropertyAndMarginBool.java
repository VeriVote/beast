package edu.pse.beast.highlevel;

import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

/**
 * The Class PropertyAndMarginBool.
 */
public class PropertyAndMarginBool {

    /** The description. */
    private PreAndPostConditionsDescription description;

    /** The margin status. */
    private boolean marginStatus;

    /**
     * Instantiates a new property and margin bool.
     *
     * @param descr the descr
     * @param marginStat the margin stat
     */
    public PropertyAndMarginBool(final PreAndPostConditionsDescription descr,
                                 final boolean marginStat) {
        this.description = descr;
        this.marginStatus = marginStat;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public PreAndPostConditionsDescription getDescription() {
        return description;
    }

    /**
     * Gets the margin status.
     *
     * @return the margin status
     */
    public boolean getMarginStatus() {
        return marginStatus;
    }
}
