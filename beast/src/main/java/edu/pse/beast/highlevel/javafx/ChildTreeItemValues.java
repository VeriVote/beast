package edu.pse.beast.highlevel.javafx;

import java.util.ArrayList;

import edu.pse.beast.propertychecker.Result;

/**
 * The Class ChildTreeItemValues.
 */
public class ChildTreeItemValues {

    /** The property name. */
    private final String propertyName;

    /** The check box status. */
    private final boolean checkBoxStatus;

    /** The disabled. */
    private final boolean disabled;

    /** The results. */
    private final ArrayList<Result> results;

    /**
     * Instantiates a new child tree item values.
     *
     * @param propertyNameStr
     *            the property name str
     * @param checkBoxStat
     *            the check box stat
     * @param disabledAttribute
     *            the disabled attribute
     * @param resultList
     *            the result list
     */
    public ChildTreeItemValues(final String propertyNameStr,
                               final boolean checkBoxStat,
                               final boolean disabledAttribute,
                               final ArrayList<Result> resultList) {
        this.propertyName = propertyNameStr;
        this.checkBoxStatus = checkBoxStat;
        this.disabled = disabledAttribute;

        this.results = resultList;
    }

    /**
     * Gets the property name.
     *
     * @return the property name
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * Gets the check box status.
     *
     * @return the check box status
     */
    public boolean getCheckBoxStatus() {
        return checkBoxStatus;
    }

    /**
     * Checks if is disabled.
     *
     * @return true, if is disabled
     */
    public boolean isDisabled() {
        return disabled;
    }

    /**
     * Gets the results.
     *
     * @return the results
     */
    public ArrayList<Result> getResults() {
        return results;
    }
}
