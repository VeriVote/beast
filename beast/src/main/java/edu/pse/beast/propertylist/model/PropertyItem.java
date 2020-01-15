package edu.pse.beast.propertylist.model;

import java.util.List;
import java.util.UUID;

import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.highlevel.ResultPresenterElement;
import edu.pse.beast.propertychecker.Result;

/**
 * This class wraps property descriptions of type
 * PreAndPostConditionsDescription. Further data is the test status and the
 * result of the property analysis. Also the counter example of the analysis is
 * stored here.
 *
 * @author Justin Hecht
 */
public class PropertyItem implements ResultPresenterElement {

    private PreAndPostConditionsDescription description;
    private boolean willBeTested;
    private ResultType resultType;
    private List<String> error;
    private boolean willBeMarginComputed;
    private Result result;

    /**
     * Constructor for the class.
     *
     * @param descr                The property description to add to the property item
     * @param testStatus           Sets whether the property will be analyzed in the next
     *                             check
     * @param computeMargin whether the margin will be computed
     */
    public PropertyItem(final PreAndPostConditionsDescription descr,
                        final boolean testStatus,
                        final boolean computeMargin) {
        description = descr;
        willBeTested = testStatus;

        this.willBeMarginComputed = computeMargin;

        resultType = ResultType.UNTESTED;
    }

    /**
     * Constructor with one parameter sets the test status to true.
     *
     * @param descr The property description to add to the property item
     */
    public PropertyItem(final PreAndPostConditionsDescription descr) {
        this(descr, true, false);
    }

    /**
     * Constructor without parameters returns a pretty unique name for the property
     * description and a blank description.
     */
    public PropertyItem() {
        this(new PreAndPostConditionsDescription(UUID.randomUUID().toString()),
             false, false);
    }

    public Result getResult() {
        return result;
    }

    @Override
    public void presentCanceled(final boolean isTimeout) {
        if (isTimeout) {
            resultType = ResultType.TIMEOUT;
        } else {
            resultType = ResultType.CANCEL;
        }
    }

    @Override
    public void presentSuccess() {
        resultType = ResultType.SUCCESS;
    }

    @Override
    public void presentFailure(final List<String> errorList) {
        resultType = ResultType.FAILURE;
        this.setError(errorList);
    }

    @Override
    public void presentFailureExample(final Result resultVal) {
        resultType = ResultType.FAILUREEXAMPLE;
        this.result = resultVal;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int resultVal = 1;
        resultVal = prime * resultVal
                + ((description == null)
                        ? 0 : description.hashCode());
        return resultVal;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        return this.description.getName()
                .equals(((PropertyItem) o).description.getName());
    }

    // getter and setter
    public PreAndPostConditionsDescription getDescription() {
        return description;
    }

    public void setDescription(final PreAndPostConditionsDescription descr) {
        this.description = descr;
    }

//  public void setDescription(String newName, FormalPropertiesDescription preCond,
//      FormalPropertiesDescription postCond, SymbolicVariableList symVars) {
//    this.description = new PreAndPostConditionsDescription(newName, preCond, postCond, symVars);
//  }

//  public void setDescriptionName(String newName) {
//    this.description =
//        new PreAndPostConditionsDescription(
//                newName,
//                this.description.getPreConditionsDescription(),
//                this.description.getPostConditionsDescription(),
//                this.description.getSymVarList());
//  }

    public String getDescriptionName() {
        return this.description.getName();
    }

    public void setTestStatus(final boolean newStatus) {
        willBeTested = newStatus;
    }

    public void setMarginStatus(final boolean newStatus) {
        willBeMarginComputed = newStatus;
    }

    public Boolean getTestStatus() {
        return willBeTested;
    }

    public ResultType getResultType() {
        return resultType;
    }

    public void setResultType(final ResultType resType) {
        this.resultType = resType;
    }

    public List<String> getError() {
        return error;
    }

    public void setError(final List<String> errorList) {
        this.error = errorList;
    }

    public boolean getMarginStatus() {
        return willBeMarginComputed;
    }

    @Override
    public void present(final Result resultToPresent) {
        this.result = resultToPresent;
        this.resultType = ResultType.FAILUREEXAMPLE;
    }
}
