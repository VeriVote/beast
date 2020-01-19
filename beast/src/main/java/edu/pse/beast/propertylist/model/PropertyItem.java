/*
 * @author Lukas Stapelbroek
 */
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
    /** The Constant PRIME. */
    private static final int PRIME = 31;

    /** The description. */
    private PreAndPostConditionsDescription description;

    /** The will be tested. */
    private boolean willBeTested;

    /** The result type. */
    private ResultType resultType;

    /** The error. */
    private List<String> error;

    /** The will be margin computed. */
    private boolean willBeMarginComputed;

    /** The result. */
    private Result result;

    /**
     * Constructor for the class.
     *
     * @param descr
     *            The property description to add to the property item
     * @param testStatus
     *            Sets whether the property will be analyzed in the next check
     * @param computeMargin
     *            whether the margin will be computed
     */
    public PropertyItem(final PreAndPostConditionsDescription descr,
                        final boolean testStatus, final boolean computeMargin) {
        description = descr;
        willBeTested = testStatus;

        this.willBeMarginComputed = computeMargin;

        resultType = ResultType.UNTESTED;
    }

    /**
     * Constructor with one parameter sets the test status to true.
     *
     * @param descr
     *            The property description to add to the property item
     */
    public PropertyItem(final PreAndPostConditionsDescription descr) {
        this(descr, true, false);
    }

    /**
     * Constructor without parameters returns a pretty unique name for the
     * property description and a blank description.
     */
    public PropertyItem() {
        this(new PreAndPostConditionsDescription(UUID.randomUUID().toString()),
             false, false);
    }

    /**
     * Gets the result.
     *
     * @return the result
     */
    public Result getResult() {
        return result;
    }

    /**
     * Present canceled.
     *
     * @param isTimeout
     *            the is timeout
     */
    @Override
    public void presentCanceled(final boolean isTimeout) {
        if (isTimeout) {
            resultType = ResultType.TIMEOUT;
        } else {
            resultType = ResultType.CANCEL;
        }
    }

    /**
     * Present success.
     */
    @Override
    public void presentSuccess() {
        resultType = ResultType.SUCCESS;
    }

    /**
     * Present failure.
     *
     * @param errorList
     *            the error list
     */
    @Override
    public void presentFailure(final List<String> errorList) {
        resultType = ResultType.FAILURE;
        this.setError(errorList);
    }

    /**
     * Present failure example.
     *
     * @param resultVal
     *            the result val
     */
    @Override
    public void presentFailureExample(final Result resultVal) {
        resultType = ResultType.FAILUREEXAMPLE;
        this.result = resultVal;
    }

    /**
     * Hash code.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        int resultVal = 1;
        resultVal = PRIME * resultVal
                + ((description == null) ? 0 : description.hashCode());
        return resultVal;
    }

    /**
     * Equals.
     *
     * @param o
     *            the o
     * @return true, if successful
     */
    @Override
    public boolean equals(final Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        return this.description.getName()
                .equals(((PropertyItem) o).description.getName());
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    // getter and setter
    public PreAndPostConditionsDescription getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param descr
     *            the new description
     */
    public void setDescription(final PreAndPostConditionsDescription descr) {
        this.description = descr;
    }

    // public void setDescription(String newName, FormalPropertiesDescription preCond,
    //                            FormalPropertiesDescription postCond,
    //                            SymbolicVariableList symVars) {
    //     this.description = new PreAndPostConditionsDescription(newName, preCond,
    //                                                            postCond, symVars);
    // }

    // public void setDescriptionName(String newName) {
    //     this.description =
    //         new PreAndPostConditionsDescription(
    //                 newName,
    //                 this.description.getPreConditionsDescription(),
    //                 this.description.getPostConditionsDescription(),
    //                 this.description.getSymVarList());
    // }

    /**
     * Gets the description name.
     *
     * @return the description name
     */
    public String getDescriptionName() {
        return this.description.getName();
    }

    /**
     * Sets the test status.
     *
     * @param newStatus
     *            the new test status
     */
    public void setTestStatus(final boolean newStatus) {
        willBeTested = newStatus;
    }

    /**
     * Sets the margin status.
     *
     * @param newStatus
     *            the new margin status
     */
    public void setMarginStatus(final boolean newStatus) {
        willBeMarginComputed = newStatus;
    }

    /**
     * Gets the test status.
     *
     * @return the test status
     */
    public Boolean getTestStatus() {
        return willBeTested;
    }

    /**
     * Gets the result type.
     *
     * @return the result type
     */
    public ResultType getResultType() {
        return resultType;
    }

    /**
     * Sets the result type.
     *
     * @param resType
     *            the new result type
     */
    public void setResultType(final ResultType resType) {
        this.resultType = resType;
    }

    /**
     * Gets the error.
     *
     * @return the error
     */
    public List<String> getError() {
        return error;
    }

    /**
     * Sets the error.
     *
     * @param errorList
     *            the new error
     */
    public void setError(final List<String> errorList) {
        this.error = errorList;
    }

    /**
     * Gets the margin status.
     *
     * @return the margin status
     */
    public boolean getMarginStatus() {
        return willBeMarginComputed;
    }

    /**
     * Present.
     *
     * @param resultToPresent
     *            the result to present
     */
    @Override
    public void present(final Result resultToPresent) {
        this.result = resultToPresent;
        this.resultType = ResultType.FAILUREEXAMPLE;
    }
}
