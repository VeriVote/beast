package edu.pse.beast.codearea.errorhandling;

import java.util.ArrayList;

/**
 * This interface is implemented by all classes which can find errors in code.
 *
 * @author Holger Klein
 */
public interface ErrorFinder {

    /**
     * Gets the errors.
     *
     * @return the errors
     */
    ArrayList<CodeError> getErrors();
}
