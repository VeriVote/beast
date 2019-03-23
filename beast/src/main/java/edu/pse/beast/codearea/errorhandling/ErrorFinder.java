package edu.pse.beast.codearea.errorhandling;

import java.util.ArrayList;

/**
 * This interface is implemented by all classes which can find errors in code
 * @author Holger-Desktop
 */
public interface ErrorFinder {
    public ArrayList<CodeError> getErrors();
}
