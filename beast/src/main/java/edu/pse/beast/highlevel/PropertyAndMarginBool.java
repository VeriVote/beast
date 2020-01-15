package edu.pse.beast.highlevel;

import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

public class PropertyAndMarginBool {
    private PreAndPostConditionsDescription description;
    private boolean marginStatus;

    public PropertyAndMarginBool(final PreAndPostConditionsDescription descr,
                                 final boolean marginStat) {
        this.description = descr;
        this.marginStatus = marginStat;
    }

    public PreAndPostConditionsDescription getDescription() {
        return description;
    }

    public boolean getMarginStatus() {
        return marginStatus;
    }
}
