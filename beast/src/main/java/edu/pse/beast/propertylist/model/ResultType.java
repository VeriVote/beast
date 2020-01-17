package edu.pse.beast.propertylist.model;

/**
 * Gives the result type of the property analysis.
 *
 * @author Justin Hecht
 */
public enum ResultType {
    /**
     * The property has not been analyzed yet.
     */
    UNTESTED,

    /**
     * The property has been analyzed but the result is not available right now.
     */
    TESTED,

    /**
     * The analysis was timed out.
     */
    TIMEOUT,

    /**
     * The analysis was canceled (probably by the user).
     */
    CANCEL,

    /**
     * The analysis revealed that the election description fulfills the
     * property.
     */
    SUCCESS,

    /**
     * The analysis could not be done because there was an error.
     */
    FAILURE,

    /**
     * The analysis revealed that the election description does not fulfill the
     * property.
     */
    FAILUREEXAMPLE
}
