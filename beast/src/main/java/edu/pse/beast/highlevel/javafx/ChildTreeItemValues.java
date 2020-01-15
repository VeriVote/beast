package edu.pse.beast.highlevel.javafx;

import java.util.ArrayList;

import edu.pse.beast.propertychecker.Result;

public class ChildTreeItemValues {

    private final String propertyName;
    private final boolean checkBoxStatus;
    private final boolean disabled;

    private final ArrayList<Result> results;

    public ChildTreeItemValues(final String propertyNameStr,
                               final boolean checkBoxStat,
                               final boolean disabledAttribute,
                               final ArrayList<Result> resultList) {
        this.propertyName = propertyNameStr;
        this.checkBoxStatus = checkBoxStat;
        this.disabled = disabledAttribute;

        this.results = resultList;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public boolean getCheckBoxStatus() {
        return checkBoxStatus;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public ArrayList<Result> getResults() {
        return results;
    }
}
