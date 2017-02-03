package edu.pse.beast.highlevel;

import java.util.List;

import edu.pse.beast.datatypes.FailureExample;

/**
 * 
 * @author Lukas
 *
 */
public interface ResultPresenterElement {
    
    /**
     * presents that a timeOut stopped the checking
     */
    void presentTimeOut();
    
    /**
     * presents that the check was successfull
     */
    void presentSuccess();
    
    /**
     * presents that the check was a failure
     */
    void presentFailure(List<String> error);
    
    /**
     * presents the example that fails the property
     * @param example the example to be presented
     */
    void presentFailureExample(FailureExample example);
}
