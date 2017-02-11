package edu.pse.beast.highlevel;

import java.util.List;

import edu.pse.beast.datatypes.FailureExample;

/**
 * Element which is specifically used to display a result of a check to the user.
 * @author Lukas
 *
 */
public interface ResultPresenterElement {
    
    /**
     * presents that a timeOut stopped the checking
     * @param isTimeout true, if it was a timeout, false if it was by the user
     */
    void presentCanceled(boolean isTimeout);
    
    /**
     * presents that the check was successfull
     */
    void presentSuccess();
    
    /**
     * presents that the check was a failure
     * @param error what exactly went wrong
     */
    void presentFailure(List<String> error);
    
    /**
     * presents the example that fails the property
     * @param example the example to be presented
     */
    void presentFailureExample(FailureExample example);
}
